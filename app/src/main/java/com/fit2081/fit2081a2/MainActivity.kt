package com.fit2081.fit2081a2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fit2081.fit2081a2.ui.theme.FIT2081A2Theme
import com.fit2081.fit2081a2.ui.screens.WelcomeScreen
import com.fit2081.fit2081a2.ui.screens.LoginScreen
import com.fit2081.fit2081a2.ui.screens.QuestionScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT2081A2Theme {
                val navController = rememberNavController()
                val context = LocalContext.current

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "welcome") {
                        composable("welcome") {
                            WelcomeScreen(navController = navController, modifier = Modifier.padding(innerPadding))
                        }
                        composable("login") {
                            LoginScreen(navController = navController, context = context, modifier = Modifier.padding(innerPadding))
                        }
                        composable("questions") {
                            QuestionScreen(navController = navController, context = context, modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}