package com.nedash.pressure.blood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nedash.pressure.blood.databinding.FragmentDetailsBinding
import com.nedash.pressure.blood.utils.convertLongToDate
import com.nedash.pressure.blood.utils.convertLongToTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val binding by lazy { FragmentDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetailsViewModel>()
    private val navArgs by navArgs<DetailsFragmentArgs>()

    private lateinit var selectedBloodPressure: BloodPressureEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        selectedBloodPressure = navArgs.pressure
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectData()

        initNumberPickersWithData(selectedBloodPressure)

        initNumberPickersListeners()
        initDateAndTime()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        selectedBloodPressure.time =
            arguments?.getLong("time") ?: selectedBloodPressure.time
    }

    private fun collectData() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>("date")
            ?.observe(viewLifecycleOwner) {
                selectedBloodPressure.date = it
                binding.tvDate.text = convertLongToDate(selectedBloodPressure.date)
            }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>("time")
            ?.observe(viewLifecycleOwner) {
                selectedBloodPressure.time = it
                binding.tvTime.text = convertLongToTime(selectedBloodPressure.time)
            }

        lifecycleScope.launch {
            viewModel.isDataChanged.collectLatest { isDataChanged ->
                if (isDataChanged)
                    findNavController().popBackStack()
            }
        }
    }

    private fun initNumberPickersWithData(selectedBloodPressure: BloodPressureEntity) {
        with(binding) {
            npDiastolic.minValue = 0
            npDiastolic.maxValue = 300
            npDiastolic.value = selectedBloodPressure.diastolic

            npSystolic.minValue = 0
            npSystolic.maxValue = 300
            npSystolic.value = selectedBloodPressure.systolic

            npPulse.minValue = 0
            npPulse.maxValue = 300
            npPulse.value = selectedBloodPressure.pulse
        }
    }

    private fun initNumberPickersListeners() {
        with(binding) {
            npDiastolic.setOnValueChangedListener { _, _, _ ->
                selectedBloodPressure.diastolic = npDiastolic.value
            }

            npSystolic.setOnValueChangedListener { _, _, _ ->
                selectedBloodPressure.systolic = npSystolic.value
            }

            npPulse.setOnValueChangedListener { _, _, _ ->
                selectedBloodPressure.pulse = npPulse.value
            }
        }
    }

    private fun initDateAndTime() {
        with(binding) {
            tvDate.text = convertLongToDate(selectedBloodPressure.date)
            tvTime.text = convertLongToTime(selectedBloodPressure.time)

            tvDate.setOnClickListener {
                val datePickerFragment = DatePickerFragment()

                datePickerFragment.show(
                    childFragmentManager,
                    DatePickerFragment::class.java.canonicalName
                )
            }

            tvTime.setOnClickListener {
                val timePickerFragment = TimePickerFragment()

                timePickerFragment.show(
                    childFragmentManager,
                    TimePickerFragment::class.java.canonicalName
                )
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSave.setOnClickListener {
                viewModel.addNewOrUpdate(selectedBloodPressure)
            }
        }
    }
}