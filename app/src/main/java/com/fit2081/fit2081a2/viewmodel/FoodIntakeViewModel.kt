package com.fit2081.fit2081a2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.db.entities.FoodIntake
import com.fit2081.fit2081a2.data.repository.FoodIntakeRepository
import kotlinx.coroutines.launch

class FoodIntakeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FoodIntakeRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = FoodIntakeRepository(db.foodIntakeDao())
    }

    fun insert(foodIntake: FoodIntake) {
        viewModelScope.launch {
            repository.insert(foodIntake)
        }
    }

    fun getByPatientId(patientId: Int, onResult: (FoodIntake?) -> Unit) {
        viewModelScope.launch {
            val intake = repository.getByPatientId(patientId)
            onResult(intake)
        }
    }
}