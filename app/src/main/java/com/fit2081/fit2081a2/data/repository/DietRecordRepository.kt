package com.fit2081.fit2081a2.data.repository

import com.fit2081.fit2081a2.data.db.dao.DietRecordDao
import com.fit2081.fit2081a2.data.db.entities.DietRecord

class DietRecordRepository(private val dietRecordDao: DietRecordDao) {

    suspend fun insert(record: DietRecord): Long {
        return dietRecordDao.insert(record)
    }

    suspend fun getByPatientId(patientId: Int): DietRecord? {
        return dietRecordDao.getByPatientId(patientId)
    }

    suspend fun deleteAll() = dietRecordDao.deleteAll()

}