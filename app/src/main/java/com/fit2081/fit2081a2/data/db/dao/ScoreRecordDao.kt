package com.fit2081.fit2081a2.data.db.dao

import androidx.room.*
import com.fit2081.fit2081a2.data.db.entities.ScoreRecord

@Dao
interface ScoreRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: ScoreRecord): Long

    @Query("SELECT * FROM ScoreRecord WHERE patientId = :patientId")
    suspend fun getByPatientId(patientId: Int): List<ScoreRecord>

    @Query("""
    SELECT sr.*
    FROM ScoreRecord sr
    INNER JOIN Patient p ON sr.patientId = p.patientId
    WHERE p.userId = :userId
    LIMIT 1
""")
    suspend fun getScoreRecordByUserId(userId: Int): ScoreRecord?

    @Query("DELETE FROM ScoreRecord")
    suspend fun deleteAll()

}