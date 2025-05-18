package com.fit2081.fit2081a2.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.UserViewModel
import com.fit2081.fit2081a2.ui.components.DropDownBar
import com.fit2081.fit2081a2.utils.readCSVFile
import com.fit2081.fit2081a2.viewmodel.UserLoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    context: Context,
    userLoginViewModel: UserLoginViewModel,
    modifier: Modifier = Modifier
) {
    var selectedUserID: Any? by remember { mutableStateOf(null) }
    var PhoneNumber by remember { mutableStateOf("") }
    var csvData by remember { mutableStateOf<Map<String, Map<String, String>>>(emptyMap()) }
    val userIDs = remember { mutableStateListOf<Int>() }

//    LaunchedEffect(Unit) {
//        csvData = readCSVFile(context, "data.csv")
//        if (csvData.isNotEmpty()) {
//            userIDs.addAll(csvData.keys)
//        }
//    }

    LaunchedEffect(Unit) {
        val ids = userLoginViewModel.getAllUserIds()  // List<Int>
        userIDs.clear()
        userIDs.addAll(ids)
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

            DropDownBar(
                label = "Select your ID",
                elements = userIDs,
                selectedValue = selectedUserID,
                onSelectionChanged = { selectedUserID = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Phone Number",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
            )

            OutlinedTextField(
                value = PhoneNumber,
                onValueChange = {
                    // 仅允许输入数字，并限制长度不超过 11
                    if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                        PhoneNumber = it
                    }
                },
                label = { Text("Enter your number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                isError = PhoneNumber.length != 11 && PhoneNumber.isNotEmpty(),
                supportingText = {
                    if (PhoneNumber.isNotEmpty() && PhoneNumber.length != 11) {
                        Text("Phone number must be exactly 11 digits")
                    }
                }
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
//                        userViewModel.updateUserID(selectedUserID)
                        navController.navigate("questions")
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

            Button(
                onClick = {
                    navController.navigate("register")
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
                    "Register",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }
        }
    }
}