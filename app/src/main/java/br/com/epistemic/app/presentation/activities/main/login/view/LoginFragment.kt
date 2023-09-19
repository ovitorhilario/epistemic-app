package br.com.epistemic.app.presentation.activities.main.login.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import br.com.epistemic.app.R
import br.com.epistemic.app.databinding.FragmentLoginBinding
import br.com.epistemic.app.commom.ext.load
import br.com.epistemic.app.commom.ext.showError
import br.com.epistemic.app.commom.ext.showSuccess
import br.com.epistemic.app.commom.flags.Event
import br.com.epistemic.app.commom.flags.InvalidEntry
import br.com.epistemic.app.commom.singleton.Assets
import br.com.epistemic.app.presentation.activities.main.login.viewmodel.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, saved: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, group, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is Event.Success -> {
                    val action = LoginFragmentDirections.actionLoginToHome()
                    findNavController().navigate(action)
                }
                is Event.Failure -> {
                    when(event.error) {
                        InvalidEntry.Email -> {
                            binding.inputLoginEmail.setError(getString(R.string.invalid_email))
                        }
                        InvalidEntry.Password -> {
                            binding.inputLoginPassword.setError(getString(R.string.invalid_password))
                        }
                        InvalidEntry.AuthExceedLimit -> {
                            requireActivity().showError(getString(R.string.block_account))
                        }
                        else -> requireActivity().showError(getString(R.string.error_on_login))
                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.ivLoginLogo.load(Assets.EpistemicLogo)
    }

    private fun setupClickListeners() {
        binding.btnLoginSubmit.setOnClickListener {
            binding.inputLoginEmail.clearError()
            binding.inputLoginPassword.clearError()

            val email = binding.inputLoginEmail.getText()
            val password = binding.inputLoginPassword.getText()
            viewModel.login(email, password)
        }

        binding.tvLoginRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToRegister()
            findNavController().navigate(action)
        }

        binding.tvLoginForgotPassword.setOnClickListener {
            val previousEmail = binding.inputLoginEmail.getText()
            val action = LoginFragmentDirections.actionLoginToForgotPassword(previousEmail)
            findNavController().navigate(action)
        }

        binding.tvTermsOfUse.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.terms_of_use))
            .setMessage(getString(R.string.terms_of_use_desc))
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val url = "https://github.com/ovitorhilario/epistemic-app"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
            .show()
    }
}