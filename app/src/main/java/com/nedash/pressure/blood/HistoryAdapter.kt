package com.nedash.pressure.blood

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nedash.pressure.blood.databinding.ItemBpmResultBinding
import com.nedash.pressure.blood.utils.convertLongToDate
import com.nedash.pressure.blood.utils.convertLongToTime

class HistoryAdapter(
    private val size: Int? = null,
    private val onItemClicked: (BloodPressureEntity) -> Unit = {}
) : ListAdapter<BloodPressureEntity, HistoryAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<BloodPressureEntity>() {
        override fun areItemsTheSame(
            oldItem: BloodPressureEntity,
            newItem: BloodPressureEntity
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: BloodPressureEntity,
            newItem: BloodPressureEntity
        ) = oldItem.hashCode() == newItem.hashCode()
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBpmResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.root.setOnClickListener {
            val bloodPressure = it.tag as? BloodPressureEntity
            if (bloodPressure != null) {
                onItemClicked(bloodPressure)
            }
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun getItemCount(): Int = if(currentList.size <= 3) currentList.size else size ?: currentList.size

    inner class ViewHolder(private val binding: ItemBpmResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(bloodPressure: BloodPressureEntity) {
            with(binding) {
                root.tag = bloodPressure

                tvDiastolic.text = bloodPressure.diastolic.toString()
                tvSystolic.text = bloodPressure.systolic.toString()

                val time = convertLongToTime(bloodPressure.time)
                val date = convertLongToDate(bloodPressure.date)

                tvDate.text = "$time, $date"

                tvPulse.text =
                    context.getString(R.string.pulse_s, bloodPressure.pulse.toString())
            }
        }
    }
}