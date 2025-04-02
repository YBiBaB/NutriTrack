package com.fit2081.fit2081a2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.R

@Composable
fun HomeScreen(
    navController: NavController,
    currentUserID: String,
    csvData: Map<String, Map<String, String>>,
) {
    val scrollState = rememberScrollState()
    val currentUserData = csvData[currentUserID]
    val sex = currentUserData?.get("Sex")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp, top = 30.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "Hello,",
                fontSize = 24.sp,
                color = Color.Gray
            )
            Text(
                text = currentUserID,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    "You've already filled in your Food intake Questionnaire, " +
                        "but you can change details here.",
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        navController.navigate("questions")
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width(95.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F29BD)
                    ),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit Icon",
                            tint = Color.White,  // 设置图标颜色
                            modifier = Modifier.size(20.dp)  // 图标的大小
                        )

                        Text(
                            "Edit",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.homeimage),
                contentDescription = "Home image",
                modifier = Modifier
                    .size(370.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            ) {
                Text(
                    "My Score",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable{}
                ) {
                    Text(
                        "See all scores",
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight, // 右箭头
                        contentDescription = "Arrow",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.LightGray, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowUp,
                            contentDescription = "Up Arrow",
                            tint = Color.Gray,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Your Food Quality Score",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }

                if (sex != null) {
                    val score = when (sex) {
                        "Male" -> currentUserData["HEIFAtotalscoreMale"]
                        "Female" -> currentUserData["HEIFAtotalscoreFemale"]
                        else -> "error score"
                    }
                    Text(
                        text = "$score/100",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                } else {
                    Text(
                        text = "Error Sex",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "What is the food quality score?",
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text("Your Food Quality Score provides a snapshot of " +
                    "how well your eating pattens align with established food guidelines," +
                    "helping you identify bot strengths and opportunities for improvement in your diet."
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("This personalized measurement considers various food groups " +
                    "including vegetables, fruits, whole grains, and proteins " +
                    "to give you practical insights for making healthier food choices."
            )
        }
    }
}