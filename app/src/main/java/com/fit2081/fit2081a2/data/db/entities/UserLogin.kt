package com.fit2081.fit2081a2.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserLogin")
data class UserLogin (
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,  // Auto increment PK

    val username: String,  // User_ID in CSV
    val passwordHash: String,  // Hash value of password
)