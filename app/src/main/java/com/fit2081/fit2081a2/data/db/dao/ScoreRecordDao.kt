package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import com.fit2081.fit2081a2.data.db.entities.ScoreRecord

@Dao
interface ScoreRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: ScoreRecord): Long

    @Query("SELECT * FROM ScoreRecord WHERE patientId = :patientId")
    suspend fun getByPatientId(patientId: Int): List<ScoreRecord>

    @Query("DELETE FROM ScoreRecord")
    suspend fun deleteAll()

}