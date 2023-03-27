package com.nong.flowsample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var count: MutableLiveData<Int> = MutableLiveData(0)

    private var countJob: Job? = null

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> get() = _isRunning

    private fun countingFlow(): Flow<Int> = flow {
        while (isRunning.value) {
            count.value = count.value?.plus(1)
            delay(1000L)
        }
    }

    fun startCount() {
        countJob = viewModelScope.launch {
            _isRunning.value = true
            countingFlow().launchIn(this)
        }
    }

    fun stopCount() {
        _isRunning.value = false
        countJob?.cancel()
        countJob = null
    }

    fun resetCounter(){
        count.value = 0
    }
}