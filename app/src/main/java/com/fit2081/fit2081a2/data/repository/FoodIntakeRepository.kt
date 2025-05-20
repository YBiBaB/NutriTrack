package com.fit2081.fit2081a2.data.repository

import com.fit2081.fit2081a2.data.db.dao.FoodIntakeDao
import com.fit2081.fit2081a2.data.db.entities.FoodIntake

class FoodIntakeRepository(private val foodIntakeDao: FoodIntakeDao) {

    suspend fun insert(foodIntake: FoodIntake) {
        foodIntakeDao.insert(foodIntake)
    }

    suspend fun updateFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.update(foodIntake)
    }

    suspend fun getByPatientId(patientId: Int): FoodIntake? {
        return foodIntakeDao.getByPatientId(patientId)
    }

    suspend fun deleteAll() = foodIntakeDao.deleteAll()

}