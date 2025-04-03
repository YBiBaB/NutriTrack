package com.fit2081.fit2081a2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InsightsScreen(
    modifier: Modifier,
    currentUserID: String,
    csvData: Map<String, Map<String, String>>,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val scrollState = rememberScrollState()
        val currentUserData = csvData[currentUserID]
        val sex = currentUserData?.get("Sex")

        val tagsMap = mapOf(
            "Vegetables" to listOf("VegetablesHEIFAscoreMale", "VegetablesHEIFAscoreFemale"),
            "Fruit" to listOf("FruitHEIFAscoreMale", "FruitHEIFAscoreFemale"),
            "Grains & Cereals" to listOf("GrainsandcerealsHEIFAscoreMale", "GrainsandcerealsHEIFAscoreFemale"),
            "Whole Grains" to listOf("WholegrainsHEIFAscoreMale", "WholegrainsHEIFAscoreFemale"),
            "Meat & Alternatives" to listOf("MeatandalternativesHEIFAscoreMale", "MeatandalternativesHEIFAscoreFemale"),
            "Dairy" to listOf("DairyandalternativesHEIFAscoreMale", "DairyandalternativesHEIFAscoreFemale"),
            "Sodium" to listOf("SodiumHEIFAscoreMale", "SodiumHEIFAscoreFemale"),
            "Alcohol" to listOf("AlcoholHEIFAscoreMale", "AlcoholHEIFAscoreFemale"),
            "Water" to listOf("WaterHEIFAscoreMale", "WaterHEIFAscoreFemale"),
            "Sugar" to listOf("SugarHEIFAscoreMale" ,"SugarHEIFAscoreFemale"),
            "Saturated Fat" to listOf("SaturatedFatHEIFAscoreMale", "SaturatedFatHEIFAscoreFemale"),
            "UnsaturatedFat" to listOf("UnsaturatedFatHEIFAscoreMale", "UnsaturatedFatHEIFAscoreFemale"),
            "Discretionary" to listOf("DiscretionaryHEIFAscoreMale", "DiscretionaryHEIFAscoreFemale"),
        )
        val totalMarkTags = listOf("HEIFAtotalscoreMale", "HEIFAtotalscoreFemale")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(35.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            tagsMap.forEach { (tag, fieldNames) ->
                val fieldName = if (sex == "Male") fieldNames[0] else fieldNames[1]

                val score = currentUserData?.get(fieldName)?.toIntOrNull() ?: 0
                val progress = score / 10f

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tag,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    LinearProgressIndicator(
                        progress = progress.coerceIn(0f, 1f),
                        modifier = Modifier
                            .weight(3f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(text = "$score/10", modifier = Modifier.padding(start = 8.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total Food Quality Score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            val fieldName = if (sex == "Male") totalMarkTags[0] else totalMarkTags[1]
            val score = currentUserData?.get(fieldName)?.toIntOrNull() ?: 0
            val progress = score / 100f

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = progress.coerceIn(0f, 1f),
                    modifier = Modifier
                        .weight(3f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(text = "$score/100", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F29BD)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    "Share with someone",
                    color = Color.White,
                    fontSize = 15.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F29BD)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp,
                    contentDescription = "Improve Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                androidx.compose.material3.Text(
                    "Improve my diet!",
                    color = Color.White,
                    fontSize = 15.sp,
                )
            }
        }
    }
}