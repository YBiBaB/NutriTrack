package com.fit2081.fit2081a2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.db.entities.UserLogin
import com.fit2081.fit2081a2.data.repository.UserLoginRepository
import kotlinx.coroutines.launch

class UserLoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserLoginRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = UserLoginRepository(database.userLoginDao())
    }

    fun login(userId: Int, password: String, onResult: (UserLogin?) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(userId, password)
            onResult(user)
        }
    }

    fun register(userId: Int, password: String, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val insertedId = repository.register(userId, password)
            onResult(insertedId)
        }
    }
}