package com.fit2081.fit2081a2.utils

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    // Convert data from String to Json
    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return Gson().toJson(list)
    }

    // Convert data from Json to String
    @TypeConverter
    fun toStringList(json: String?): List<String> {
        return if (json.isNullOrEmpty()) emptyList()
        else Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? {
        return value?.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }
}