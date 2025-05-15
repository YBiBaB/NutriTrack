package com.fit2081.fit2081a2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.db.entities.ScoreRecord
import com.fit2081.fit2081a2.data.repository.ScoreRecordRepository
import kotlinx.coroutines.launch

class ScoreRecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ScoreRecordRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ScoreRecordRepository(db.scoreRecordDao())
    }

    fun insert(record: ScoreRecord, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.insert(record)
            onResult(id)
        }
    }

    fun getByPatientId(patientId: Int, onResult: (List<ScoreRecord>) -> Unit) {
        viewModelScope.launch {
            val records = repository.getByPatientId(patientId)
            onResult(records)
        }
    }
}