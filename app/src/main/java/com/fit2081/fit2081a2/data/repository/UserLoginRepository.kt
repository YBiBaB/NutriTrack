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

    suspend fun login(userId: Int, plainPassword: String): UserLogin? {
        val hashedPassword = userLoginDao.hashPassword(plainPassword)
        return withContext(Dispatchers.IO) {
            userLoginDao.login(userId, hashedPassword)
        }
    }

    suspend fun getAllUserIds(): List<Int> = userLoginDao.getAllUserIds()

    suspend fun register(userId: Int, password: String): Long {
        val hashedPassword = userLoginDao.hashPassword(password)
        val user = UserLogin(userId = userId, passwordHash = hashedPassword)
        return withContext(Dispatchers.IO) {
            userLoginDao.insert(user)
        }
    }

    suspend fun updatePassword(userId: Int, plainPassword: String) {
        val hashed = userLoginDao.hashPassword(plainPassword)
        userLoginDao.updatePassword(userId, hashed)
    }

    suspend fun deleteAll() = userLoginDao.deleteAll()
}