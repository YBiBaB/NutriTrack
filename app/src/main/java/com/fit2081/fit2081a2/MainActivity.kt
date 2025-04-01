package com.fit2081.fit2081a2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fit2081.fit2081a2.ui.theme.FIT2081A2Theme
import com.fit2081.fit2081a2.ui.screens.WelcomeScreen
import com.fit2081.fit2081a2.ui.screens.LoginScreen
import com.fit2081.fit2081a2.ui.screens.QuestionScreen
import com.fit2081.fit2081a2.ui.components.TopBar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT2081A2Theme {
                val navController = rememberNavController()
                val context = LocalContext.current

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {}
                ) { innerPadding ->
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