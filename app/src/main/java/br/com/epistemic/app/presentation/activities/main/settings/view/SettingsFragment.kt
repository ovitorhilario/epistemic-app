package br.com.epistemic.app.presentation.activities.main.settings.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import br.com.epistemic.app.R
import br.com.epistemic.app.databinding.FragmentSettingsBinding
import br.com.epistemic.app.domain.repository.LANGUAGES
import br.com.epistemic.app.domain.repository.UserPreferences
import br.com.epistemic.app.presentation.activities.main.MainActivity
import br.com.epistemic.app.presentation.activities.main.settings.viewmodel.SettingsViewModel
import br.com.epistemic.app.commom.ext.load
import br.com.epistemic.app.commom.singleton.Assets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, saved: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, group, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupView() {
        binding.ivSettingsIcon.load(Assets.EpistemicIcon)
    }

    private fun setupObservers() {
        viewModel.userPrefs.observe(viewLifecycleOwner) { userPrefs ->
            setupOptions(userPrefs)
        }

        viewModel.messages.observe(viewLifecycleOwner) { message ->
            Toast.makeText(
                requireActivity(),
                message, Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupOptions(userPrefs: UserPreferences) {
        val (medicines, exercises, languageTag, notificationsPerDay) = userPrefs

        // Medicines and Exercises
        binding.switchSettingsMedicines.isChecked = medicines
        binding.switchSettingsExercises.isChecked = exercises

        // LanguageTag
        val radioLanguageId = when (languageTag) {
            LANGUAGES.PT_BR.tag -> R.id.radio_btn_languages_pt_br
            LANGUAGES.EN_US.tag -> R.id.radio_btn_languages_en_us
            else -> R.id.radio_btn_languages_pt_br
        }
        binding.radioGroupSettingsLanguage.check(radioLanguageId)

        // Notifications Per Day
        val radioNotificationsId = when (notificationsPerDay) {
            4 -> R.id.radio_btn_notifications_opt_1
            2 -> R.id.radio_btn_notifications_opt_2
            1 -> R.id.radio_btn_notifications_opt_3
            0 -> R.id.radio_btn_notifications_opt_4
            else -> R.id.radio_btn_notifications_opt_4
        }
        binding.radioGroupSettingsNotifications.check(radioNotificationsId)
    }

    private fun setupClickListeners() {
        binding.btnSettingsBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Medicines and Exercises
        binding.switchSettingsMedicines.setOnClickListener { viewModel.toggleMedicine() }
        binding.switchSettingsExercises.setOnClickListener { viewModel.toggleExercises() }

        // LanguageTag
        binding.radioGroupSettingsLanguage.setOnCheckedChangeListener { group, checkedId ->
            val tag = when (checkedId) {
                R.id.radio_btn_languages_pt_br -> LANGUAGES.PT_BR.tag
                R.id.radio_btn_languages_en_us -> LANGUAGES.EN_US.tag
                else -> LANGUAGES.PT_BR.tag
            }
            val success = viewModel.updateLanguage(tag)
            if (success) refresh()
        }

        // Notifications Per Day
        binding.radioGroupSettingsNotifications.setOnCheckedChangeListener { group, checkedId ->
            val qnt = when (checkedId) {
                R.id.radio_btn_notifications_opt_1 -> 4
                R.id.radio_btn_notifications_opt_2 -> 2
                R.id.radio_btn_notifications_opt_3 -> 1
                R.id.radio_btn_notifications_opt_4 -> 0
                else -> 0
            }

            if (qnt != 0) {
                if (!hasPermissions()) {
                    requestPostNotifications()
                    binding.radioGroupSettingsNotifications.check(R.id.radio_btn_notifications_opt_4)
                } else {
                    viewModel.updateNotifications(qnt)
                }
            }
        }
    }

    private fun hasPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    private fun requestPostNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(
                requireActivity(),
                getString(R.string.granted_permissions),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.not_granted_permissions),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun refresh() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}