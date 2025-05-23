package com.fit2081.fit2081a2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.db.entities.UserLogin
import com.fit2081.fit2081a2.data.repository.UserLoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserLoginRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = UserLoginRepository(database.userLoginDao())
    }

    suspend fun getAllUserIds(): List<Int> = repository.getAllUserIds()

    fun login(userId: Int, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(userId, password)  // Return UserLogin?
            onResult(user != null) // Success if not null
        }
    }

    fun register(userId: Int, password: String, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val insertedId = repository.register(userId, password)
            onResult(insertedId)
        }
    }

    fun updatePassword(userId: Int, plainPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePassword(userId, plainPassword)
        }
    }
}