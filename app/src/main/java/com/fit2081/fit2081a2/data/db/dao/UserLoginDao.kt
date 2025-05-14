package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import java.security.MessageDigest
import com.fit2081.fit2081a2.data.db.entities.UserLogin

@Dao
interface UserLoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userLogin: UserLogin)

    @Query("SELECT * FROM UserLogin WHERE userId = :userId")
    suspend fun getById(userId: Int): UserLogin?

    @Query("SELECT * FROM UserLogin WHERE username = :username AND passwordHash = :password")
    suspend fun login(username: String, password: String): UserLogin?

    // Password hash verification
    @Transaction
    suspend fun loginWithHashedPassword(username: String, password: String): UserLogin? {
        val hashedPassword = hashPassword(password) // Hash password
        return login(username, hashedPassword)
    }

    // Function to encrypt password by SHA-256
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}