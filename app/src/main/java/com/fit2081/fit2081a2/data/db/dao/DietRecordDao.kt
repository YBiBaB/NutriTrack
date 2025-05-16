package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import com.fit2081.fit2081a2.data.db.entities.DietRecord

@Dao
interface DietRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: DietRecord): Long

    @Query("SELECT * FROM DietRecord WHERE patientId = :patientId")
    suspend fun getByPatientId(patientId: Int): DietRecord?

    @Query("DELETE FROM DietRecord")
    suspend fun deleteAll()

}