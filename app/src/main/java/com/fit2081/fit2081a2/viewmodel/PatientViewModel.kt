package com.fit2081.fit2081a2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.db.entities.Patient
import com.fit2081.fit2081a2.data.repository.PatientRepository
import kotlinx.coroutines.launch

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PatientRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PatientRepository(database.patientDao())
    }

    fun insert(patient: Patient) {
        viewModelScope.launch {
            repository.insert(patient)
        }
    }

    suspend fun getPhoneNumberByUserId(userId: Int): String? {
        return repository.getPhoneNumberByUserId(userId)
    }

    suspend fun getPatientIdByUserId(userId: Int): Int? {
        return repository.getPatientByUserId(userId)?.patientId
    }

    fun getPatientById(id: Int, onResult: (Patient?) -> Unit) {
        viewModelScope.launch {
            val patient = repository.getById(id)
            onResult(patient)
        }
    }

    fun getAllPatients(onResult: (List<Patient>) -> Unit) {
        viewModelScope.launch {
            val list = repository.getAll()
            onResult(list)
        }
    }
}