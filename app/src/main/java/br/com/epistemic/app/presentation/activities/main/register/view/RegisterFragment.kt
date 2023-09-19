package br.com.epistemic.app.presentation.activities.main.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import br.com.epistemic.app.R
import br.com.epistemic.app.commom.ext.load
import br.com.epistemic.app.commom.ext.showError
import br.com.epistemic.app.commom.ext.showSuccess
import br.com.epistemic.app.commom.flags.Event
import br.com.epistemic.app.commom.flags.InvalidEntry
import br.com.epistemic.app.databinding.FragmentRegisterBinding
import br.com.epistemic.app.commom.singleton.Assets
import br.com.epistemic.app.presentation.activities.main.register.viewmodel.RegisterViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, saved: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, group, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupView() {
        binding.ivRegisterLogo.load(Assets.EpistemicLogo)
    }

    private fun setupObservers() {
        viewModel.events.observe(viewLifecycleOwner) { events ->
            when(events) {
                is Event.Success -> requireActivity().showSuccess(getString(R.string.success_on_register))
                is Event.Failure -> {
                    when (events.error) {
                        InvalidEntry.Empty -> binding.inputRegisterName.setError(getString(R.string.empty_name))
                        InvalidEntry.Email -> binding.inputRegisterEmail.setError(getString(R.string.invalid_email))
                        InvalidEntry.Password -> binding.inputRegisterPassword.setError(getString(R.string.invalid_password))
                        InvalidEntry.PasswordNotMatch -> requireActivity().showError(getString(R.string.password_not_match))
                        else -> requireActivity().showError(getString(R.string.error_on_register))
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnRegisterSubmit.setOnClickListener {
            binding.inputRegisterName.clearError()
            binding.inputRegisterEmail.clearError()
            binding.inputRegisterPassword.clearError()
            binding.inputRegisterConfirmPassword.clearError()

            val name = binding.inputRegisterName.getText()
            val email = binding.inputRegisterEmail.getText()
            val password = binding.inputRegisterPassword.getText()
            val confirmEmail = binding.inputRegisterConfirmPassword.getText()

            viewModel.register(name, email, password, confirmEmail)
        }
    }
}