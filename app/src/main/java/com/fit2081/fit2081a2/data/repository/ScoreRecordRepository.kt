package com.fit2081.fit2081a2.data.repository

import com.fit2081.fit2081a2.data.db.dao.ScoreRecordDao
import com.fit2081.fit2081a2.data.db.entities.ScoreRecord

class ScoreRecordRepository(private val scoreRecordDao: ScoreRecordDao) {
    suspend fun insert(record: ScoreRecord): Long {
        return scoreRecordDao.insert(record)
    }

    suspend fun getByPatientId(patientId: Int): List<ScoreRecord> {
        return scoreRecordDao.getByPatientId(patientId)
    }

    suspend fun deleteAll() = scoreRecordDao.deleteAll()
}