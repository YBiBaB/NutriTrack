package com.fit2081.fit2081a2.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserLogin")
data class UserLogin (
    @PrimaryKey
    val userId: Int,  // User_ID in CSV, PK
    val passwordHash: String,  // Hash value of password
)