package com.fit2081.fit2081a2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.db.entities.NutriCoachTip
import com.fit2081.fit2081a2.data.repository.NutriCoachTipRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutriCoachTipViewModel (application: Application) : AndroidViewModel(application){
    private val repository: NutriCoachTipRepository

    private val _tips = MutableStateFlow<List<NutriCoachTip>>(emptyList())
    val tips: StateFlow<List<NutriCoachTip>> = _tips

    init {
        val tipDao = AppDatabase.getDatabase(application).NutriCoachTipDao()
        repository = NutriCoachTipRepository(tipDao)
    }

    fun loadTips(patientId: Int) {
        viewModelScope.launch {
            _tips.value = repository.getTipsByPatientId(patientId)
        }
    }

    fun addTip(tip: NutriCoachTip) {
        viewModelScope.launch {
            repository.insertTip(tip)
            loadTips(tip.patientId)
        }
    }

    fun clearTips(patientId: Int) {
        viewModelScope.launch {
            repository.deleteTipsByPatientId(patientId)
            loadTips(patientId)
        }
    }
}