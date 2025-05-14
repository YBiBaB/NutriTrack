package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import com.fit2081.fit2081a2.data.db.entities.UserLogin

@Dao
interface UserLoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userLogin: UserLogin)

    @Query("SELECT * FROM UserLogin WHERE userId = :userId")
    suspend fun getById(userId: Int): UserLogin?

    @Query("SELECT * FROM UserLogin WHERE username = :username AND passwordHash = :password")
    suspend fun login(username: String, password: String): UserLogin?
}