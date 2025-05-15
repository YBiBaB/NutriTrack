package com.fit2081.fit2081a2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.db.entities.DietRecord
import com.fit2081.fit2081a2.data.repository.DietRecordRepository
import kotlinx.coroutines.launch

class DietRecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DietRecordRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = DietRecordRepository(db.dietRecordDao())
    }

    fun insert(record: DietRecord, onResult: (Long?) -> Unit) {
        viewModelScope.launch {
            val id = repository.insert(record)
            onResult(id)
        }
    }

    fun getByPatientId(patientId: Int, onResult: (DietRecord?) -> Unit) {
        viewModelScope.launch {
            val record = repository.getByPatientId(patientId)
            onResult(record)
        }
    }
}