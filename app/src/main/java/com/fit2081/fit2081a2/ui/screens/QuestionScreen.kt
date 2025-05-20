package com.fit2081.fit2081a2.ui.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.UserViewModel
import com.fit2081.fit2081a2.data.db.entities.FoodIntake
import com.fit2081.fit2081a2.ui.components.*
import com.fit2081.fit2081a2.utils.UserSessionManager
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@SuppressLint("MutableCollectionMutableState")
@Composable
fun QuestionScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    context: Context,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var currentlyShowingKey by remember { mutableStateOf<String?>(null) }
    var modalTitle by remember { mutableStateOf("") }
    var modalImage by remember { mutableStateOf("") }
    var modalMessage by remember { mutableStateOf("") }
    val userID = UserSessionManager.getLoggedInUserId(context)

    var userResponses by rememberSaveable { mutableStateOf(mutableMapOf<String, Any>()) }

    var missingFields by remember { mutableStateOf(setOf<String>()) }
    var duplicateTimeFields by remember { mutableStateOf(setOf<String>()) }

    val foodOptions = listOf("Fruits", "Vegetables", "Grains", "Red Meat", "Seafood", "Poultry", "Fish", "Egg", "Nuts/Seeds")
    val selectedFoodCategories = foodOptions.filter { userResponses[it] == true }
    val foodIntake = userID?.let {
        FoodIntake(
            patientId = it,
            foodCategories = selectedFoodCategories,
            persona = userResponses["persona"] as? String ?: "",
            biggestMealTime = userResponses["mealTime"] as? LocalTime ?: LocalTime.of(0, 0),
            sleepTime = userResponses["sleepTime"] as? LocalTime ?: LocalTime.of(0, 0),
            wakeUpTime = userResponses["wakeTime"] as? LocalTime ?: LocalTime.of(0, 0),
        )
    }

    val personaTypes = mapOf(
        "Health Devotee" to mapOf(
            "imageName" to "persona_1",
            "text" to "I’m passionate about healthy eating & health plays a big part in my life. " +
                    "I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. " +
                    "I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy."
        ),
        "Mindful Eater" to mapOf(
            "imageName" to "persona_2",
            "text" to "I’m health-conscious and being healthy and eating healthy is important to me. " +
                    "Although health means different things to different people, " +
                    "I make conscious lifestyle decisions about eating based on what I believe healthy means. " +
                    "I look for new recipes and healthy eating information on social media."
        ),
        "Wellness Striver" to mapOf(
            "imageName" to "persona_3",
            "text" to "I aspire to be healthy (but struggle sometimes). " +
                    "Healthy eating is hard work! " +
                    "I’ve tried to improve my diet, " +
                    "but always find things that make it difficult to stick with the changes. " +
                    "Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go."
        ),
        "Balance Seeker" to mapOf(
            "imageName" to "persona_4",
            "text" to "I try and live a balanced lifestyle, " +
                    "and I think that all foods are okay in moderation. " +
                    "I shouldn’t have to feel guilty about eating a piece of cake now and again. " +
                    "I get all sorts of inspiration from social media like finding out about new restaurants, " +
                    "fun recipes and sometimes healthy eating tips."
        ),
        "Health Procrastinator" to mapOf(
            "imageName" to "persona_5",
            "text" to "I’m contemplating healthy eating but it’s not a priority for me right now. " +
                    "I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. " +
                    "I have taken a few steps to be healthier but I am not motivated to make it a high priority " +
                    "because I have too many other things going on in my life."
        ),
        "Food Carefree" to mapOf(
            "imageName" to "persona_6",
            "text" to "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. " +
                    "I don’t really notice healthy eating tips or recipes and I don’t care what I eat."
        )
    )

//    LaunchedEffect(userID) {
//        userViewModel.usersResponsesMap[userID.toString()]?.let {
//            userResponses = it.toMutableMap()
//        }
//    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            if (missingFields.isNotEmpty() || duplicateTimeFields.isNotEmpty()) {
                Text(
                    text = "Please complete all required fields and avoid duplicate times.",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Text(
                "Tick all food categories you can eat",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            foodOptions.chunked(3).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowItems.forEach { food ->
                        Checkbox(
                            checked = userResponses[food] as? Boolean ?: false,
                            onCheckedChange = { checked ->
                                userResponses = userResponses.toMutableMap().apply { put(food, checked) }
                            }
                        )
                        Text(food)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Your Persona",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Text(
                "People can be broadly classified into 6 different types based on " +
                        "their eating preferences. Click on each button below to find out " +
                        "the different types, and select the type that best fits you!"
            )

            Spacer(modifier = Modifier.height(8.dp))

            personaTypes.keys.chunked(3).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowItems.forEach { title ->
                        val info = personaTypes[title] ?: return@forEach
                        Button(
                            onClick = {
                                showDialog = true
                                modalTitle = title
                                modalMessage = info["text"] ?: "No description found"
                                modalImage = info["imageName"] ?: ""
                            },
                            modifier = Modifier
                                .wrapContentSize()
                                .height(55.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F29BD)),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = title,
                                color = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (showDialog) {
                PersonaModal(
                    onDismiss = { showDialog = false },
                    title = modalTitle,
                    message = modalMessage,
                    imageName = modalImage,
                    context = context,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Which persona best fits you?",
            )

            DropDownBar(
                label = "",
                elements = personaTypes.keys.toList(),
                selectedValue =  userResponses["persona"] as? String,
                onSelectionChanged = {
                    userResponses = userResponses.toMutableMap().apply { put("persona", it) }
                },
                isError = "persona" in missingFields
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Timings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            val timeQuestions = listOf(
                "mealTime" to "What time of day approx, do you normally eat your biggest meal?",
                "sleepTime" to "What time of day approx, do you go to sleep at night?",
                "wakeTime" to "What time of day approx, do you wake up in the morning?"
            )

            timeQuestions.forEach { (key, question) ->
                val interactionSource = remember { MutableInteractionSource() }
                val timeStr = userResponses[key] as? String ?: ""
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val initialTime = try {
                    LocalTime.parse(timeStr, formatter)
                } catch (e: Exception) {
                    LocalTime.now()
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(question, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    Box {
                        OutlinedTextField(
                            value = userResponses[key] as? String ?: "",
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("00:00") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "Clock Icon")
                            },
                            isError = key in missingFields || key in duplicateTimeFields,
                            modifier = Modifier.width(120.dp)
                        )

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable {
                                    currentlyShowingKey = key
                                }
                        )
                    }

                    if (currentlyShowingKey == key) {
                        TimePicker(
                            initialTime = initialTime,
                            show = true,
                            onDismissRequest = { currentlyShowingKey = null },
                            onTimeSelected = { selectedTime ->
                                userResponses = userResponses.toMutableMap().apply {
                                    put(key, selectedTime.format(formatter))
                                }
                                currentlyShowingKey = null
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val requiredKeys = listOf("mealTime", "sleepTime", "wakeTime", "persona")
                    val newMissing = mutableSetOf<String>()

                    requiredKeys.forEach { key ->
                        if ((userResponses[key] as? String).isNullOrBlank()) newMissing.add(key)
                    }

                    val selectedFoods = foodOptions.any { userResponses[it] == true }
                    if (!selectedFoods) newMissing.add("food")

                    val timeValues = timeQuestions.map { (k, _) -> k to (userResponses[k] as? String ?: "") }
                    val duplicates = timeValues.groupBy { it.second }
                        .filter { it.key.isNotBlank() && it.value.size > 1 }
                        .flatMap { it.value.map { it.first } }
                        .toSet()

                    missingFields = newMissing
                    duplicateTimeFields = duplicates

                    if (missingFields.isEmpty() && duplicateTimeFields.isEmpty()) {
//                        userViewModel.updateUsersResponsesMap(
//                            userID = userID.toString(),
//                            userResponse = userResponses
//                        )
                        navController.navigate("home")
                    }
                },
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
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Save Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "Save",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }
        }
    }
}