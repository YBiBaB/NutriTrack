package com.fit2081.fit2081a2.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "DietRecord",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["patientId"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DietRecord(
    @PrimaryKey(autoGenerate = true)
    val dietId: Int = 0,    // A_I PK

    val patientId: Int,  // FK to Patient

    val discretionaryServeSize: Double,
    val vegetablesWithLegumesAllocatedServeSize: Double,
    val legumesAllocatedVegetables: Double,
    val vegetablesVariationsScore: Double,
    val vegetablesCruciferous: Double,
    val vegetablesTuberAndBulb: Double,
    val vegetablesOther: Double,
    val legumes: Double,
    val vegetablesGreen: Double,
    val vegetablesRedAndOrange: Double,

    val fruitServeSize: Double,
    val fruitVariationsScore: Double,
    val fruitPome: Double,
    val fruitTropicalAndSubtropical: Double,
    val fruitBerry: Double,
    val fruitStone: Double,
    val fruitCitrus: Double,
    val fruitOther: Double,

    val grainsAndCerealsServeSize: Double,
    val grainsAndCerealsNonWholeGrains: Double,
    val wholeGrainsServeSize: Double,

    val meatAndAlternativesWithLegumesAllocatedServeSize: Double,
    val legumesAllocatedMeatAndAlternatives: Double,

    val dairyAndAlternativesServeSize: Double,

    val sodiumMgMilligrams: Double,
    val alcoholStandardDrinks: Double,

    val water: Double,
    val waterTotalMl: Double,
    val beverageTotalMl: Double,

    val sugar: Double,
    val saturatedFat: Double,
    val unsaturatedFatServeSize: Double
)
