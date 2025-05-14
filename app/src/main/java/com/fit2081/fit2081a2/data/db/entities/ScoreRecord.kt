package com.fit2081.fit2081a2.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ScoreRecord",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["patientId"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScoreRecord(
    @PrimaryKey(autoGenerate = true)
    val scoreId: Int = 0,       // A_I PK

    val patientId: Int,         // FK of Patient

    val heifaScore: Double,     // Combined score for HEIFA (based on gender during insert)
    val discretionaryHeifaScore: Double,   // Combined score for Discretionary HEIFA (based on gender during insert)
    val vegetablesHeifaScore: Double,     // Combined score for Vegetables HEIFA (based on gender during insert)
    val vegetablesVariationsScore: Double, // Score of vegetables variations (no gender difference)
    val fruitHeifaScore: Double,          // Combined score for Fruit HEIFA (based on gender during insert)
    val fruitVariationsScore: Double,       // Score of fruit variations (no gender difference)
    val grainsAndCerealsHeifaScore: Double, // Combined score for Grains and Cereals HEIFA (based on gender during insert)
    val wholeGrainsHeifaScore: Double,    // Combined score for Whole Grains HEIFA (based on gender during insert)
    val meatAndAlternativesHeifaScore: Double, // Combined score for Meat and Alternatives HEIFA (based on gender during insert)
    val dairyAndAlternativesHeifaScore: Double, // Combined score for Dairy and Alternatives HEIFA (based on gender during insert)
    val sodiumHeifaScore: Double,         // Combined score for Sodium HEIFA (based on gender during insert)
    val alcoholHeifaScore: Double,       // Combined score for Alcohol HEIFA (based on gender during insert)
    val waterHeifaScore: Double,         // Combined score for Water HEIFA (based on gender during insert)
    val sugarHeifaScore: Double,         // Combined score for Sugar HEIFA (based on gender during insert)
    val saturatedFatHeifaScore: Double,  // Combined score for Saturated Fat HEIFA (based on gender during insert)
    val unsaturatedFatHeifaScore: Double // Combined score for Unsaturated Fat HEIFA (based on gender during insert)
)
