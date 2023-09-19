package br.com.epistemic.app.presentation.activities.main.forgot_password.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import br.com.epistemic.app.R
import br.com.epistemic.app.commom.ext.load
import br.com.epistemic.app.commom.ext.showError
import br.com.epistemic.app.commom.ext.showSuccess
import br.com.epistemic.app.commom.flags.Event
import br.com.epistemic.app.commom.flags.InvalidEntry
import br.com.epistemic.app.databinding.FragmentForgotPasswordBinding
import br.com.epistemic.app.commom.singleton.Assets
import br.com.epistemic.app.presentation.activities.main.forgot_password.viewmodel.ForgetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val args: ForgotPasswordFragmentArgs by navArgs()
    private val viewModel: ForgetPasswordViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, saved: Bundle?): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, group, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupView() {
        binding.ivForgotPasswordLogo.load(Assets.EpistemicLogo)
        args.previousEmail?.takeIf { it.isNotBlank() }?.let { email ->
            binding.inputForgotPasswordEmail.setText(email)
        }
    }

    private fun setupObservers() {
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is Event.Success -> {
                    requireActivity().showSuccess(getString(R.string.success_on_send_link))
                }
                is Event.Failure -> {
                    when(event.error) {
                        InvalidEntry.Email -> {
                            binding.inputForgotPasswordEmail.setError(getString(R.string.invalid_email))
                        }
                        else -> requireActivity().showError(getString(R.string.error_on_send_email))
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnForgotPasswordSubmit.setOnClickListener {
            binding.inputForgotPasswordEmail.clearError()
            val email = binding.inputForgotPasswordEmail.getText()
            viewModel.sendRecoveryEmail(email)
        }
    }
}