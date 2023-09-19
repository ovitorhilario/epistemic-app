package br.com.epistemic.app.presentation.activities.main.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import br.com.epistemic.app.R
import br.com.epistemic.app.commom.ext.getWelcomeMessage
import br.com.epistemic.app.databinding.FragmentHomeBinding
import br.com.epistemic.app.presentation.activities.main.home.adapter.HomeAdapter
import br.com.epistemic.app.presentation.activities.main.home.model.Action
import br.com.epistemic.app.presentation.activities.main.home.model.HomeItem
import br.com.epistemic.app.commom.singleton.Assets
import br.com.epistemic.app.presentation.activities.main.home.viewmodel.HomeViewModel
import br.com.epistemic.app.presentation.receivers.impl.AlarmSchedulerImpl
import br.com.epistemic.app.presentation.receivers.model.AlarmItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, saved: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, group, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userPrefs.observe(viewLifecycleOwner) { prefs ->
            val welcomeMessage = getWelcomeMessage(requireContext(), prefs.languageTag)
            setupAdapter(welcomeMessage)
        }
    }

    private fun setupAdapter(welcomeMessage: String) {
        val actions = arrayListOf<Action>(
            Action(
                type = getString(R.string.to_add),
                description = getString(R.string.add_crisis),
                imageUrl = Assets.EpilepsyIllustration,
                backgroundRes = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_wave_1),
                onClick = {}
            ),
            Action(
                type = getString(R.string.to_add),
                description = getString(R.string.add_crisis),
                imageUrl = Assets.EpilepsyIllustration,
                backgroundRes = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_wave_2),
                onClick = {}
            ),
            Action(
                type = getString(R.string.to_add),
                description = getString(R.string.add_crisis),
                imageUrl = Assets.EpilepsyIllustration,
                backgroundRes = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_wave_3),
                onClick = {}
            ),
        )

        val items = arrayListOf<HomeItem>(
            HomeItem.TopAppBar(
                onClick = {
                    val action = HomeFragmentDirections.actionHomeToSettings()
                    findNavController().navigate(action)
                }
            ),
            HomeItem.Welcome(getString(R.string.hello) + ", " + "Vitor", welcomeMessage),
            HomeItem.Actions(actions)
        )

        val adapter = HomeAdapter(items)
        binding.rvHome.adapter = adapter
    }
}