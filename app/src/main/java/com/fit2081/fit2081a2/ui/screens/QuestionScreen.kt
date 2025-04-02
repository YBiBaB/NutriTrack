package com.fit2081.fit2081a2.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.fit2081.fit2081a2.ui.components.DropDownBar
import com.fit2081.fit2081a2.ui.components.TopBar
import com.fit2081.fit2081a2.ui.components.PersonaModal

@SuppressLint("MutableCollectionMutableState")
@Composable
fun QuestionScreen(
    navController: NavController,
    onSubmit: (result: MutableMap<String, Any>) -> Unit,
    context: Context,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var modalTitle by remember { mutableStateOf("") }
    var modalImage by remember { mutableStateOf("") }
    var modalMessage by remember { mutableStateOf("") }
    var userResponses by rememberSaveable { mutableStateOf(mutableMapOf<String, Any>()) }

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
//            TopBar(
//                title = "Food intake Questionnaire",
//                navController = navController,
//                onBackClick = true,
//            )

            Text(
                "Tick all food categories you can eat",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            val foodOptions = listOf("Fruits", "Vegetables", "Grains", "Red Meat", "Seafood", "Poultry", "Fish", "Egg", "Nuts/Seeds")

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

            val personaTypes = mapOf(
                "Health Devotee" to mapOf(
                    "imageName" to "persona_1",
                    "text" to "Loves eating healthy food!"
                ),
                "Mindful Eater" to mapOf(
                    "imageName" to "persona_2",
                    "text" to "Pays attention to what they eat!"
                ),
                "Wellness Striver" to mapOf(
                    "imageName" to "persona_3",
                    "text" to "Strives for a balanced diet!"
                ),
                "Balance Seeker" to mapOf(
                    "imageName" to "persona_4",
                    "text" to "Wants a balance in diet!"
                ),
                "Health Procrastinator" to mapOf(
                    "imageName" to "persona_5",
                    "text" to "Plans to eat healthily but delays!"
                ),
                "Food Carefree" to mapOf(
                    "imageName" to "persona_6",
                    "text" to "Eats whatever they like!"
                )
            )

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
                onSelectionChanged = { userResponses["persona"] = it },
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(question, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = userResponses[key] as? String ?: "",
                        onValueChange = { userResponses = userResponses.toMutableMap().apply { put(key, it) } },
                        placeholder = { Text("00:00") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "Clock Icon")
                        },
                        modifier = Modifier.width(120.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onSubmit(userResponses)
//                    Log.d("UserResponses", userResponses.toString())
                    navController.navigate("home")
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