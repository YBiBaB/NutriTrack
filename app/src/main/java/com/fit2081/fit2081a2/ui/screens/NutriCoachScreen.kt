package com.fit2081.fit2081a2.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.fit2081.fit2081a2.network.genAI.GenAIViewModel
import com.fit2081.fit2081a2.network.genAI.UiState
import com.fit2081.fit2081a2.utils.UserSessionManager
import com.fit2081.fit2081a2.viewmodel.FruitViewModel
import com.fit2081.fit2081a2.viewmodel.PatientViewModel
import com.fit2081.fit2081a2.viewmodel.ScoreRecordViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun NutriCoachScreen(
    navController: NavController,
    scoreRecordViewModel: ScoreRecordViewModel,
    genAIViewModel: GenAIViewModel,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val userId = UserSessionManager.getLoggedInUserId(context)
    val fruitHeifaScore = remember { mutableStateOf<Double?>(null) }
    var fruitName by remember { mutableStateOf("") }
    var showTipDialog by remember { mutableStateOf(false) }
    val viewModel: FruitViewModel = viewModel()
    val fruitInfo = viewModel.fruit
    val isLoading = viewModel.isLoading
    val uiState by genAIViewModel.uiState.collectAsState()
    var result by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        if (userId != null) {
            fruitHeifaScore.value = scoreRecordViewModel.getScoreValue(userId, "fruitHeifaScore")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
            .padding(35.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        ) {
            if (fruitHeifaScore.value != null) {
                if (fruitHeifaScore.value!! >= 5) {
                    Text("Your fruit intake is very healthy!", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = "https://picsum.photos/300",
                        contentDescription = "Random healthy image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        "Fruit Name",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = fruitName,
                            onValueChange = { fruitName = it },
                            label = { Text("Enter fruit name") },
                            modifier = Modifier
                                .weight(1f)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                if (fruitName.isEmpty()) {
                                    Toast.makeText(context, "Fruit name must not be blank", Toast.LENGTH_SHORT).show()
                                } else {
                                    viewModel.fetchFruit(fruitName, context)
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .height(50.dp)
                            ,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF5F29BD)
                            )
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Search Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Details", color = Color.White, fontSize = 18.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            val family = fruitInfo?.family ?: ""
                            val calories = fruitInfo?.nutritions?.calories?.toString() ?: ""
                            val fat = fruitInfo?.nutritions?.fat?.toString() ?: ""
                            val sugar = fruitInfo?.nutritions?.sugar?.toString() ?: ""
                            val carbs = fruitInfo?.nutritions?.carbohydrates?.toString() ?: ""
                            val protein = fruitInfo?.nutritions?.protein?.toString() ?: ""

                            NutritionRow(label = "Family", value = family)
                            NutritionRow(label = "Calories", value = if (calories.isNotEmpty()) "$calories kcal" else "")
                            NutritionRow(label = "Fat", value = if (fat.isNotEmpty()) "$fat g" else "")
                            NutritionRow(label = "Sugar", value = if (sugar.isNotEmpty()) "$sugar g" else "")
                            NutritionRow(label = "Carbohydrates", value = if (carbs.isNotEmpty()) "$carbs g" else "")
                            NutritionRow(label = "Protein", value = if (protein.isNotEmpty()) "$protein g" else "")
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 16.dp))

                    Button(
                        onClick = {
                            genAIViewModel.sendPrompt(
                                "Generate a short encouraging message to help someone improve their fruit intake."
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(50.dp)
                        ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5F29BD)
                        )
                    ) {
                        if (uiState is UiState.Loading) {
                            // 显示加载圈
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier
                                    .size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                "Generating...",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "AI Icon",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                "Motivational Message (AI)",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        var textColor = MaterialTheme.colorScheme.onSurface
                        if (uiState is UiState.Error) {
                            textColor = MaterialTheme.colorScheme.error
                            result = (uiState as UiState.Error).errorMessage
                        } else if (uiState is UiState.Success) {
                            result = (uiState as UiState.Success).outputString
                        }

                        Text(
                            text = result,
                            textAlign = TextAlign.Start,
                            color = textColor,
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(
                            onClick = {
                                showTipDialog = true
                            },
                            modifier = Modifier
                                .height(50.dp)
                            ,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF5F29BD)
                            )
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Show all tips"
                            )
                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                "Show all tips",
                                color = Color.White,
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold)
        Text(text = value)
    }
}
