package com.fit2081.fit2081a2.data.repository

import com.fit2081.fit2081a2.data.db.dao.NutriCoachTipDao
import com.fit2081.fit2081a2.data.db.entities.NutriCoachTip

class NutriCoachTipRepository(private val tipDao: NutriCoachTipDao) {
    suspend fun insertTip(tip: NutriCoachTip) = tipDao.insertTip(tip)

    suspend fun getTipsByPatientId(patientId: Int): List<NutriCoachTip> =
        tipDao.getTipsByPatientId(patientId)

    suspend fun deleteTipsByPatientId(patientId: Int) =
        tipDao.deleteTipsByPatientId(patientId)
}