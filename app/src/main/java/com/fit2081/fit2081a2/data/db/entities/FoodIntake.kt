package com.fit2081.fit2081a2.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(
    tableName = "FoodIntake",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["patientId"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FoodIntake(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,                        // A_I PK

    val patientId: Int,                     // FK of Patient
    val foodCategories: List<String>,       // Muti answer question (food categories)
    val persona: String,                    // Muti choice question (persona)
    val biggestMealTime: LocalTime,         // Biggest meal time
    val sleepTime: LocalTime,               // Sleep time
    val wakeUpTime: LocalTime,              // Wake up time
)
