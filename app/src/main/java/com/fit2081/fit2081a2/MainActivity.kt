package com.fit2081.fit2081a2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fit2081.fit2081a2.data.db.AppDatabase
import com.fit2081.fit2081a2.data.repository.*
import com.fit2081.fit2081a2.ui.theme.FIT2081A2Theme
import com.fit2081.fit2081a2.ui.screens.*
import com.fit2081.fit2081a2.ui.components.*
import com.fit2081.fit2081a2.utils.*
import com.fit2081.fit2081a2.viewmodel.*
import kotlinx.coroutines.launch


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            DataImportManager.importIfNeeded(
                context = this@MainActivity,
                fileName = "data.csv",
                userLoginRepo = UserLoginRepository(AppDatabase.getDatabase(this@MainActivity).userLoginDao()),
                patientRepo = PatientRepository(AppDatabase.getDatabase(this@MainActivity).patientDao()),
                dietRepo = DietRecordRepository(AppDatabase.getDatabase(this@MainActivity).dietRecordDao()),
                scoreRepo = ScoreRecordRepository(AppDatabase.getDatabase(this@MainActivity).scoreRecordDao())
            )
        }

//        lifecycleScope.launch {
//            DataImportManager.resetImportStatus(
//                context = this@MainActivity,
//                userLoginRepo = UserLoginRepository(AppDatabase.getDatabase(this@MainActivity).userLoginDao()),
//                patientRepo = PatientRepository(AppDatabase.getDatabase(this@MainActivity).patientDao()),
//                dietRepo = DietRecordRepository(AppDatabase.getDatabase(this@MainActivity).dietRecordDao()),
//                scoreRepo = ScoreRecordRepository(AppDatabase.getDatabase(this@MainActivity).scoreRecordDao())
//            )
//        }

        enableEdgeToEdge()
        setContent {
            FIT2081A2Theme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val csvData = readCSVFile(context, "data.csv")
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val userViewModel: UserViewModel = viewModel()
                val userLoginViewModel: UserLoginViewModel = viewModel()
                val patientViewModel: PatientViewModel = viewModel()
                val scoreRecordViewModel: ScoreRecordViewModel = viewModel()
                val foodIntakeViewModel: FoodIntakeViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        val showTopBar = when (currentRoute) {
                            "welcome" -> false
                            "login" -> false
                            "questions" -> true
                            "home" -> false
                            "insights" -> true
                            else -> false
                        }

                        val title = when (currentRoute) {
                            "questions" -> "Food intake Questionnaire"
                            "insights" -> "Insights: Food Score"
                            else -> ""
                        }

                        val showBackButton = when (currentRoute) {
                            "questions" -> true
                            "insights" -> false
                            else -> false
                        }

                        val backRoute = when (currentRoute) {
                            "questions" -> "login"
                            else -> null
                        }

                        if (showTopBar) {
                            TopBar(
                                title = title,
                                navController = navController,
                                backRoute = backRoute,
                                showBackButton = showBackButton,
                            )
                        }
                    },
                    bottomBar = {
                        val items = listOf(
                            BottomNavItem(
                                label = "Home",
                                outlinedIcon = Icons.Outlined.Home,
                                filledIcon = Icons.Filled.Home,
                                route = "home",
                            ),
                            BottomNavItem(
                                label = "Insights",
                                outlinedIcon = Icons.Outlined.Info,
                                filledIcon = Icons.Filled.Info,
                                route = "insights"
                            ),
                            BottomNavItem(
                                label = "NutriCoach",
                                outlinedIcon = Icons.Outlined.Face,
                                filledIcon = Icons.Filled.Face,
                                route = "nutriCoach"
                            ),
                            BottomNavItem(
                                label = "Settings",
                                outlinedIcon = Icons.Outlined.Settings,
                                filledIcon = Icons.Filled.Settings,
                                route = "settings"
                            ),
                        )

                        if (shouldShowBottomBar(currentRoute)) {
                            BottomBar(
                                navController = navController,
                                items = items,
                                color = Color(0xFF5F29BD)
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "welcome",
                        Modifier.padding(innerPadding),
                    ) {
                        composable("welcome") {
                            WelcomeScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                navController = navController,
                                context = context,
                                userLoginViewModel = userLoginViewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                navController = navController,
                                context = context,
                                userLoginViewModel = userLoginViewModel,
                                patientViewModel = patientViewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("questions") {
                            QuestionScreen(
                                navController = navController,
                                patientViewModel = patientViewModel,
                                foodIntakeViewModel = foodIntakeViewModel,
                                context = context,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                scoreRecordViewModel = scoreRecordViewModel,
                            )
                        }
                        composable("insights") {
                            InsightsScreen(
                                modifier = Modifier.padding(innerPadding),
                                currentUserID = userViewModel.userID,
                                csvData = csvData,
                            )
                        }
                        composable("nutriCoach") {
                            NutriCoachScreen()
                        }
                        composable("settings") {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }

    private fun shouldShowBottomBar(currentRoute: String?): Boolean {
        return currentRoute in listOf(
            "home",
            "insights",
            "nutriCoach",
            "settings",
        )
    }
}

@SuppressLint("MutableCollectionMutableState")
class UserViewModel : ViewModel() {
    var userID by mutableStateOf("")
        private set

    fun updateUserID(newID: String) {
        userID = newID
    }

    var usersResponsesMap: MutableMap<String, MutableMap<String, Any>> = mutableMapOf()
        private set

    fun updateUsersResponsesMap(userID: String, userResponse: MutableMap<String, Any>) {
        usersResponsesMap[userID] = userResponse
    }
}

