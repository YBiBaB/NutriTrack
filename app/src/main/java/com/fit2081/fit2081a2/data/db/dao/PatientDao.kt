package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import com.fit2081.fit2081a2.data.db.entities.Patient

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: Patient): Long

    @Query("SELECT * FROM Patient WHERE patientId = :id")
    suspend fun getById(id: Int): Patient?

    @Query("SELECT * FROM Patient")
    suspend fun getAll(): List<Patient>

    @Query("DELETE FROM Patient")
    suspend fun deleteAll()
}