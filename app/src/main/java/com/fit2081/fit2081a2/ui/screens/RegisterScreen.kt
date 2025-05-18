package com.fit2081.fit2081a2.ui.screens

import android.content.Context
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
import com.fit2081.fit2081a2.ui.components.DropDownBar
import com.fit2081.fit2081a2.viewmodel.UserLoginViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    context: Context,
    modifier: Modifier = Modifier,
    userLoginViewModel: UserLoginViewModel,
) {
    val userIDs = remember { mutableStateListOf<Int>() }
    var selectedUserID: Any? by remember { mutableStateOf(null) }
    var PhoneNumber by remember { mutableStateOf("") }

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
                "Register",
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
                "Phone number",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
            )

            OutlinedTextField(
                value = PhoneNumber,
                onValueChange = {
                    // Only numbers are allowed, and the length is limited to 11
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

            Button(
                onClick = {
                    navController.navigate("login")
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
                    "Login",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }
        }
    }
}