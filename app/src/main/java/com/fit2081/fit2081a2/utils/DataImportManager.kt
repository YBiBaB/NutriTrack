package com.fit2081.fit2081a2.utils

import android.content.Context
import android.util.Log
import com.fit2081.fit2081a2.data.db.entities.*
import com.fit2081.fit2081a2.data.repository.*

object DataImportManager {

    suspend fun importCsvDataToDb(
        context: Context,
        fileName: String,
        userLoginRepository: UserLoginRepository,
        patientRepository: PatientRepository,
        dietRecordRepository: DietRecordRepository,
        scoreRecordRepository: ScoreRecordRepository
    ) {
        val csvData = readCSVFile(context, fileName)

        for ((userId, fields) in csvData) {
            // 1. 插入 UserLogin
            val userLogin = UserLogin(username = userId, passwordHash = "")
            val insertedUserId = userLoginRepository.insert(userLogin).toInt()

            // 2. 插入 Patient
            val patient = Patient(
                userId = insertedUserId,
                name = userId, // 没有名字用User_ID占位
                phoneNumber = fields["PhoneNumber"] ?: "",
                sex = fields["Sex"] ?: ""
            )
            val patientId = patientRepository.insert(patient).toInt()

            // 3. 插入 DietRecord
            val dietRecord = DietRecord(
                patientId = patientId,
                discretionaryServeSize = fields["Discretionaryservesize"]?.toDoubleOrNull() ?: 0.0,
                vegetablesWithLegumesAllocatedServeSize = fields["Vegetableswithlegumesallocatedservesize"]?.toDoubleOrNull() ?: 0.0,
                legumesAllocatedVegetables = fields["LegumesallocatedVegetables"]?.toDoubleOrNull() ?: 0.0,
                vegetablesVariationsScore = fields["Vegetablesvariationsscore"]?.toDoubleOrNull() ?: 0.0,
                vegetablesCruciferous = fields["VegetablesCruciferous"]?.toDoubleOrNull() ?: 0.0,
                vegetablesTuberAndBulb = fields["VegetablesTuberandbulb"]?.toDoubleOrNull() ?: 0.0,
                vegetablesOther = fields["VegetablesOther"]?.toDoubleOrNull() ?: 0.0,
                legumes = fields["Legumes"]?.toDoubleOrNull() ?: 0.0,
                vegetablesGreen = fields["VegetablesGreen"]?.toDoubleOrNull() ?: 0.0,
                vegetablesRedAndOrange = fields["VegetablesRedandorange"]?.toDoubleOrNull() ?: 0.0,
                fruitServeSize = fields["Fruitservesize"]?.toDoubleOrNull() ?: 0.0,
                fruitVariationsScore = fields["Fruitvariationsscore"]?.toDoubleOrNull() ?: 0.0,
                fruitPome = fields["FruitPome"]?.toDoubleOrNull() ?: 0.0,
                fruitTropicalAndSubtropical = fields["FruitTropicalandsubtropical"]?.toDoubleOrNull() ?: 0.0,
                fruitBerry = fields["FruitBerry"]?.toDoubleOrNull() ?: 0.0,
                fruitStone = fields["FruitStone"]?.toDoubleOrNull() ?: 0.0,
                fruitCitrus = fields["FruitCitrus"]?.toDoubleOrNull() ?: 0.0,
                fruitOther = fields["FruitOther"]?.toDoubleOrNull() ?: 0.0,
                grainsAndCerealsServeSize = fields["Grainsandcerealsservesize"]?.toDoubleOrNull() ?: 0.0,
                grainsAndCerealsNonWholeGrains = fields["GrainsandcerealsNonwholegrains"]?.toDoubleOrNull() ?: 0.0,
                wholeGrainsServeSize = fields["Wholegrainsservesize"]?.toDoubleOrNull() ?: 0.0,
                meatAndAlternativesWithLegumesAllocatedServeSize = fields["Meatandalternativeswithlegumesallocatedservesize"]?.toDoubleOrNull() ?: 0.0,
                legumesAllocatedMeatAndAlternatives = fields["LegumesallocatedMeatandalternatives"]?.toDoubleOrNull() ?: 0.0,
                dairyAndAlternativesServeSize = fields["Dairyandalternativesservesize"]?.toDoubleOrNull() ?: 0.0,
                sodiumMgMilligrams = fields["Sodiummgmilligrams"]?.toDoubleOrNull() ?: 0.0,
                alcoholStandardDrinks = fields["Alcoholstandarddrinks"]?.toDoubleOrNull() ?: 0.0,
                water = fields["Water"]?.toDoubleOrNull() ?: 0.0,
                waterTotalMl = fields["WaterTotalmL"]?.toDoubleOrNull() ?: 0.0,
                beverageTotalMl = fields["BeverageTotalmL"]?.toDoubleOrNull() ?: 0.0,
                sugar = fields["Sugar"]?.toDoubleOrNull() ?: 0.0,
                saturatedFat = fields["SaturatedFat"]?.toDoubleOrNull() ?: 0.0,
                unsaturatedFatServeSize = fields["UnsaturatedFatservesize"]?.toDoubleOrNull() ?: 0.0
            )
            dietRecordRepository.insert(dietRecord)

            // 4. 插入 ScoreRecord，根据 sex 字段区分男女性别字段
            val sex = fields["Sex"] ?: "Male"
            fun pickScore(maleKey: String, femaleKey: String) =
                if (sex.equals("Male", true)) fields[maleKey]?.toDoubleOrNull() ?: 0.0 else fields[femaleKey]?.toDoubleOrNull() ?: 0.0

            val scoreRecord = ScoreRecord(
                patientId = patientId,
                heifaScore = pickScore("HEIFAtotalscoreMale", "HEIFAtotalscoreFemale"),
                discretionaryHeifaScore = pickScore("DiscretionaryHEIFAscoreMale", "DiscretionaryHEIFAscoreFemale"),
                vegetablesHeifaScore = pickScore("VegetablesHEIFAscoreMale", "VegetablesHEIFAscoreFemale"),
                vegetablesVariationsScore = fields["Vegetablesvariationsscore"]?.toDoubleOrNull() ?: 0.0,
                fruitHeifaScore = pickScore("FruitHEIFAscoreMale", "FruitHEIFAscoreFemale"),
                fruitVariationsScore = fields["Fruitvariationsscore"]?.toDoubleOrNull() ?: 0.0,
                grainsAndCerealsHeifaScore = pickScore("GrainsandcerealsHEIFAscoreMale", "GrainsandcerealsHEIFAscoreFemale"),
                wholeGrainsHeifaScore = pickScore("WholegrainsHEIFAscoreMale", "WholegrainsHEIFAscoreFemale"),
                meatAndAlternativesHeifaScore = pickScore("MeatandalternativesHEIFAscoreMale", "MeatandalternativesHEIFAscoreFemale"),
                dairyAndAlternativesHeifaScore = pickScore("DairyandalternativesHEIFAscoreMale", "DairyandalternativesHEIFAscoreFemale"),
                sodiumHeifaScore = pickScore("SodiumHEIFAscoreMale", "SodiumHEIFAscoreFemale"),
                alcoholHeifaScore = pickScore("AlcoholHEIFAscoreMale", "AlcoholHEIFAscoreFemale"),
                waterHeifaScore = pickScore("WaterHEIFAscoreMale", "WaterHEIFAscoreFemale"),
                sugarHeifaScore = pickScore("SugarHEIFAscoreMale", "SugarHEIFAscoreFemale"),
                saturatedFatHeifaScore = pickScore("SaturatedFatHEIFAscoreMale", "SaturatedFatHEIFAscoreFemale"),
                unsaturatedFatHeifaScore = pickScore("UnsaturatedFatHEIFAscoreMale", "UnsaturatedFatHEIFAscoreFemale")
            )
            scoreRecordRepository.insert(scoreRecord)
        }
    }

    suspend fun importIfNeeded(
        context: Context,
        fileName: String,
        userLoginRepo: UserLoginRepository,
        patientRepo: PatientRepository,
        dietRepo: DietRecordRepository,
        scoreRepo: ScoreRecordRepository
    ) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val imported = prefs.getBoolean("csv_imported", false)
        if (!imported) {
            try {
                importCsvDataToDb(context, fileName, userLoginRepo, patientRepo, dietRepo, scoreRepo)
                prefs.edit().putBoolean("csv_imported", true).apply()
            } catch (e: Exception) {
                Log.e("DataImport", "Fail to import CSV data to DB", e)
            }
        }
    }

    suspend fun resetImportStatus(
        context: Context,
        userLoginRepo: UserLoginRepository,
        patientRepo: PatientRepository,
        dietRepo: DietRecordRepository,
        scoreRepo: ScoreRecordRepository
    ) {
        // 删除表中所有数据（可选）
        scoreRepo.deleteAll()
        dietRepo.deleteAll()
        patientRepo.deleteAll()
        userLoginRepo.deleteAll()

        // 重置 SharedPreferences 状态
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit().putBoolean("csv_imported", false).apply()
    }
}