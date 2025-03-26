package com.fit2081.fit2081a2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.io.BufferedReader
import java.io.InputStreamReader
import com.fit2081.fit2081a2.ui.theme.FIT2081A2Theme

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
                    }
                }
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, context: Context, modifier: Modifier = Modifier) {
    var selectedUserID by remember { mutableStateOf("") }
    var PhoneNumber by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var csvData by remember { mutableStateOf<Map<String, Map<String, String>>>(emptyMap()) }
    var userIDs = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        csvData = readCSVFile(context, "data.csv")
        if (csvData.isNotEmpty()) {
            userIDs.addAll(csvData.keys)
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "Log In",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "My ID (Provided by your Clinician)",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange =  {expanded = it}
            ) {
                OutlinedTextField(
                    value = selectedUserID,
                    onValueChange = { selectedUserID = it },
                    placeholder = { Text(text = "Select your ID") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null,
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userIDs.forEach { userId ->
                        DropdownMenuItem(
                            text = {Text (userId)},
                            onClick = {
                                selectedUserID = userId
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Phone Number",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
            )

            OutlinedTextField(
                value = PhoneNumber,
                onValueChange = { PhoneNumber = it },
                label = { Text(text = "Enter your number") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("This app is only for pre-registered users. " +
                    "Please have your ID and phone number handy before continuing."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val userPhone = csvData[selectedUserID]?.get("PhoneNumber")
                    if (userPhone != null && userPhone == PhoneNumber) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
//                        context.startActivity(Intent(context, Dashboard::class.java))
                    } else {
                        Toast.makeText(context, "Incorrect phone number", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F29BD)
                )
            ) {
                Text(
                    "Continue",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}