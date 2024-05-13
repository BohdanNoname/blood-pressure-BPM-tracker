package com.nedash.pressure.blood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedash.pressure.blood.use_case.InsertBloodPressureUseCase
import com.nedash.pressure.blood.use_case.UpdateBloodPressureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val insertBloodPressureUseCloneable: InsertBloodPressureUseCase,
    private val updateBloodPressureUseCase: UpdateBloodPressureUseCase
) : ViewModel() {

    private val _isDataChanged = MutableStateFlow(false)
    val isDataChanged = _isDataChanged.asStateFlow()

    fun addNewOrUpdate(bloodPressure: BloodPressureEntity) {
        if (bloodPressure.id == -1) {
            bloodPressure.id = 0
            insert(bloodPressure)
        } else {
            update(bloodPressure)
        }
    }

    private fun insert(bloodPressure: BloodPressureEntity) = flow<Unit> {
        insertBloodPressureUseCloneable.invoke(bloodPressure)
    }.onCompletion {
        _isDataChanged.emit(true)
    }.launchIn(viewModelScope)

    private fun update(bloodPressure: BloodPressureEntity) = flow<Unit> {
        updateBloodPressureUseCase.invoke(bloodPressure)
    }.onCompletion {
        _isDataChanged.emit(true)
    }.launchIn(viewModelScope)
}