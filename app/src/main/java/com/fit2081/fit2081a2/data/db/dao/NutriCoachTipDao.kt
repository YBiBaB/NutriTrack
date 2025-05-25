package com.fit2081.fit2081a2.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fit2081.fit2081a2.data.db.entities.NutriCoachTip

@Dao
interface NutriCoachTipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTip(tip: NutriCoachTip)

    @Query("SELECT * FROM NutriCoachTip WHERE patientId = :patientId ORDER BY timestamp DESC")
    suspend fun getTipsByPatientId(patientId: Int): List<NutriCoachTip>

    @Query("DELETE FROM NutriCoachTip WHERE patientId = :patientId")
    suspend fun deleteTipsByPatientId(patientId: Int)
}