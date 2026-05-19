package com.android.nodocivico.viewmodel

import androidx.lifecycle.*
import com.android.nodocivico.data.ReportRepository
import com.android.nodocivico.model.Report
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository) : ViewModel() {

    val allReports: LiveData<List<Report>> = repository.allReports.asLiveData()

    private val _syncStatus = MutableLiveData<Boolean>()
    val syncStatus: LiveData<Boolean> get() = _syncStatus

    fun insert(report: Report) = viewModelScope.launch {
        repository.insert(report)
    }

    fun update(report: Report) = viewModelScope.launch {
        repository.update(report)
    }

    fun delete(report: Report) = viewModelScope.launch {
        repository.delete(report)
    }

    fun syncReports() = viewModelScope.launch {
        _syncStatus.value = true
        repository.syncPendingReports()
        _syncStatus.value = false
    }
}

class ReportViewModelFactory(private val repository: ReportRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}