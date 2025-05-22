package com.fit2081.fit2081a2.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fit2081.fit2081a2.data.db.dao.*
import com.fit2081.fit2081a2.data.db.entities.*
import com.fit2081.fit2081a2.utils.Converters

@Database(
    entities = [
        UserLogin::class,
        Patient::class,
        ScoreRecord::class,
        FoodIntake::class,
        DietRecord::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userLoginDao(): UserLoginDao
    abstract fun patientDao(): PatientDao
    abstract fun scoreRecordDao(): ScoreRecordDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun dietRecordDao(): DietRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "health_database"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}