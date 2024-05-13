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
import com.nedash.pressure.blood.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

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

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.bloodPressures.collectLatest { pressures ->
                if (pressures.isNotEmpty()) {
                    initRecyclerView()

                    (binding.rvLastResults.adapter as HistoryAdapter)
                        .submitList(pressures.sortedByDescending { it.modified })

                    binding.llLastChangedResults.visibility = View.VISIBLE
                    binding.tvDataIsEmpty.visibility = View.GONE
                } else {
                    binding.llLastChangedResults.visibility = View.GONE
                    binding.tvDataIsEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rvLastResults.adapter =
                HistoryAdapter(
                    size = 3,
                    onItemClicked = { navigateToDetails(it) }
                )

            rvLastResults.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToDetails(it: BloodPressureEntity) {
        val destination = MainFragmentDirections.toDetailsFragment(it)
        findNavController().navigate(destination)
    }

    private fun initListeners() {
        with(binding) {
            tvViewAll.setOnClickListener {
                val destination = MainFragmentDirections.toHistoryFragment()
                findNavController().navigate(destination)
            }

            btnAddNew.setOnClickListener {
                val destination = MainFragmentDirections.toDetailsFragment(BloodPressureEntity())
                findNavController().navigate(destination)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvLastResults.adapter = null
    }
}