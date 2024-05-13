package com.nedash.pressure.blood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nedash.pressure.blood.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllBloodPressures()
        initListeners()
        collectData()
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.bloodPressures.collectLatest { pressures ->
                if (pressures.isNotEmpty()) {
                    initRecyclerView()

                    (binding.rvResults.adapter as HistoryAdapter)
                        .submitList(pressures.sortedByDescending { it.modified })
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rvResults.adapter = HistoryAdapter(
                onItemClicked = { navigateToDetails(it) }
            )
            rvResults.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToDetails(it: BloodPressureEntity) {
        val destination = HistoryFragmentDirections.toDetailsFragment(it)
        findNavController().navigate(destination)
    }

}