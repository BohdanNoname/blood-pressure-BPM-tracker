package com.nedash.pressure.blood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedash.pressure.blood.use_case.GetAllBloodPressuresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllBloodPressuresUseCase: GetAllBloodPressuresUseCase
) : ViewModel() {

    private val _bloodPressures = MutableSharedFlow<List<BloodPressureEntity>>()
    val bloodPressures = _bloodPressures.asSharedFlow()

    fun getAllBloodPressures() = viewModelScope.launch(Dispatchers.IO) {
        _bloodPressures.emit(getAllBloodPressuresUseCase.invoke())
    }

}