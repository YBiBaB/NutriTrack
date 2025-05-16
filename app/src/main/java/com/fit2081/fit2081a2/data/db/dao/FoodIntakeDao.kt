package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import com.fit2081.fit2081a2.data.db.entities.FoodIntake

@Dao
interface FoodIntakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodIntake: FoodIntake)

    @Query("SELECT * FROM FoodIntake WHERE patientId = :patientId")
    suspend fun getByPatientId(patientId: Int): FoodIntake?

    @Query("DELETE FROM FoodIntake")
    suspend fun deleteAll()
}