package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import com.fit2081.fit2081a2.data.db.entities.DietRecord

interface DietRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: DietRecord)

    @Query("SELECT * FROM DietRecord WHERE patientId = :patientId")
    suspend fun getByPatientId(patientId: Int): DietRecord?
}