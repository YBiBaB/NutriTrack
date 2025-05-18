package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import java.security.MessageDigest
import com.fit2081.fit2081a2.data.db.entities.UserLogin

@Dao
interface UserLoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userLogin: UserLogin): Long

    @Query("SELECT * FROM UserLogin WHERE userId = :userId")
    suspend fun getById(userId: Int): UserLogin?

    @Query("SELECT userId FROM UserLogin")
    suspend fun getAllUserIds(): List<Int>

    @Query("SELECT * FROM UserLogin WHERE userId = :userId AND passwordHash = :passwordHash")
    suspend fun login(userId: Int, passwordHash: String): UserLogin?

    @Query("UPDATE UserLogin SET passwordHash = :hashedPassword WHERE userId = :userId")
    suspend fun updatePassword(userId: Int, hashedPassword: String)

    @Query("DELETE FROM UserLogin")
    suspend fun deleteAll()

    // Function to encrypt password by SHA-256
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}