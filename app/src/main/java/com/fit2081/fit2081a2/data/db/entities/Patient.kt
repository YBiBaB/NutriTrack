package com.fit2081.fit2081a2.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Patient",
    foreignKeys = [
        ForeignKey(
            entity = UserLogin::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val patientId: Int = 0,     // A_I PK

    val userId: Int,            // FK of UserLogin
    val firstName: String?,      // First name of the patient
    val lastName: String?,       // Last name of the patient
    val phoneNumber: String,    // Patient phone number
    val sex: String             // Patient gender
)
