package com.fit2081.fit2081a2.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "NutriCoachTip",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["patientId"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NutriCoachTip (
    @PrimaryKey(autoGenerate = true)
    val tipId: Int = 0,

    val patientId: Int,

    val tipContent: String,
    val scoreSnapshot: String?,
    val dietSnapshot: String?,
    val timestamp: Long = System.currentTimeMillis()
)