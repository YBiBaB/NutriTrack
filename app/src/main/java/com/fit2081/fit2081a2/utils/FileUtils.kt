package com.fit2081.fit2081a2.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

fun readCSVFile(context: Context, fileName: String):  Map<String, Map<String, String>> {
    val assets = context.assets
    val csvData = mutableMapOf<String, Map<String, String>>()
    try {
        val inputStream = assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))

        val headers = reader.readLine()?.split(",")?.map { it.trim() }

        if (headers != null) {
            val userIdIndex = headers.indexOf("User_ID")
            if (userIdIndex != -1) {
                reader.useLines { lines ->
                    lines.forEach { line ->
                        val values = line.split(",").map { it.trim() }.toTypedArray()
                        val userId = values[userIdIndex]
                        val userData = mutableMapOf<String, String>()

                        for (i in headers.indices) {
                            if (i != userIdIndex) {
                                userData[headers[i]] = values[i]
                            }
                        }

                        csvData[userId] = userData
                    }
                }
            } else {
                Log.e("CSVReader", "User_ID column not found in CSV file.")
            }
        }

    } catch (e: Exception) {
        Log.e("CSVReader", "Error reading CSV file: $fileName", e)
    }
    return csvData
}
