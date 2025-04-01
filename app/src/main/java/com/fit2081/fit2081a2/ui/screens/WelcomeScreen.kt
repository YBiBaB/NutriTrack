package com.fit2081.fit2081a2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.R

@Composable
fun WelcomeScreen(navController: NavController, modifier: Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "NutriTrack",
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp
            )
            Image(
                painter = painterResource(id = R.drawable.welcomelogo),
                contentDescription = "Welcome logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(" This app provides general health and nutrition information" +
                    "for educational purposes only. It is not intended as " +
                    "medical advice, diagnosis, or treatment. Always consult a " +
                    "qualified healthcare professional before making any " +
                    "changes to your diet, exercise, or health regimen. Use this app at your own risk. " +
                    "If you’d like to an Accredited Practicing Dietitian (APD), " +
                    "please visit the Monash Nutrition/Dietetics Clinic " +
                    "(discounted rates for students): " +
                    "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F29BD)
                )
            ) {
                Text(
                    "Login",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }

            Spacer(modifier = Modifier.height(96.dp))

            Text(
                "Designed by Juntao Liang (34152962)",
//                fontWeight = FontWeight.Bold,
            )
        }
    }
}