package com.fit2081.fit2081a2.data.repository

import com.fit2081.fit2081a2.data.db.dao.UserLoginDao
import com.fit2081.fit2081a2.data.db.entities.UserLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserLoginRepository(private val userLoginDao: UserLoginDao) {

    suspend fun insert(user: UserLogin):Long {
        return withContext(Dispatchers.IO) {
            userLoginDao.insert(user)
        }
    }

    suspend fun getById(userId: Int): UserLogin? {
        return withContext(Dispatchers.IO) {
            userLoginDao.getById(userId)
        }
    }

    suspend fun login(username: String, plainPassword: String): UserLogin? {
        return withContext(Dispatchers.IO) {
            userLoginDao.loginWithHashedPassword(username, plainPassword)
        }
    }

    suspend fun register(username: String, password: String): Long {
        val hashedPassword = userLoginDao.hashPassword(password)
        val user = UserLogin(username = username, passwordHash = hashedPassword)
        return userLoginDao.insert(user)
    }
}