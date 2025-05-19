package com.fit2081.fit2081a2.data.repository

import com.fit2081.fit2081a2.data.db.dao.ScoreRecordDao
import com.fit2081.fit2081a2.data.db.entities.ScoreRecord
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class ScoreRecordRepository(private val scoreRecordDao: ScoreRecordDao) {
    suspend fun insert(record: ScoreRecord): Long {
        return scoreRecordDao.insert(record)
    }

    suspend fun getByPatientId(patientId: Int): List<ScoreRecord> {
        return scoreRecordDao.getByPatientId(patientId)
    }

    suspend fun getScoreValueByUserIdAndField(userId: Int, fieldName: String): Double? {
        val record = scoreRecordDao.getScoreRecordByUserId(userId) ?: return null

        // Convert input field names to uppercase
        val targetFieldName = fieldName.uppercase()

        // Find fields with matching uppercase names in member properties
        val property = record::class.memberProperties.find {
            it.name.uppercase() == targetFieldName
        }

        // Get the value of the corresponding attribute in the record through reflection
        // and try to convert it to Double type
        val value = (property as? KProperty1<ScoreRecord, *>)?.get(record) as? Double

        return value
    }

    suspend fun deleteAll() = scoreRecordDao.deleteAll()
}