package com.fit2081.fit2081a2.data.repository

import com.fit2081.fit2081a2.data.db.dao.PatientDao
import com.fit2081.fit2081a2.data.db.entities.Patient

class PatientRepository(private val patientDao: PatientDao) {

    suspend fun insert(patient: Patient): Long {
        return patientDao.insert(patient)
    }

    suspend fun getPhoneNumberByUserId(userId: Int): String? {
        return patientDao.getPhoneNumberByUserId(userId)
    }

    suspend fun getPatientByUserId(userId: Int): Patient? {
        return patientDao.getPatientByUserId(userId)
    }

    suspend fun getById(id: Int): Patient? {
        return patientDao.getById(id)
    }

    suspend fun getAll(): List<Patient> {
        return patientDao.getAll()
    }

    suspend fun deleteAll() = patientDao.deleteAll()
}