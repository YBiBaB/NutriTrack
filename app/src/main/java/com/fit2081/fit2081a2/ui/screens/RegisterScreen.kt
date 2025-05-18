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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.ui.components.DropDownBar
import com.fit2081.fit2081a2.viewmodel.PatientViewModel
import com.fit2081.fit2081a2.viewmodel.UserLoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(
    navController: NavController,
    context: Context,
    modifier: Modifier = Modifier,
    userLoginViewModel: UserLoginViewModel,
    patientViewModel: PatientViewModel
) {
    val userIDs = remember { mutableStateListOf<Int>() }
    var selectedUserID: Any? by remember { mutableStateOf(null) }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val ids = userLoginViewModel.getAllUserIds()
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Phone number",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    // Only numbers are allowed, and the length is limited to 11
                    if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                        phoneNumber = it
                    }
                },
                label = { Text("Enter your number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                isError = phoneNumber.length != 11 && phoneNumber.isNotEmpty(),
                supportingText = {
                    if (phoneNumber.isNotEmpty() && phoneNumber.length != 11) {
                        Text("Phone number must be exactly 11 digits")
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Password",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isNotEmpty() && password.length < 6,
                supportingText = {
                    if (password.isNotEmpty() && password.length < 6) {
                        Text("Password length must not less than 6")
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Confirm password",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                label = { Text("Re-enter your password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                isError = confirmPassword.isNotEmpty() && confirmPassword != password,
                supportingText = {
                    if (confirmPassword.isNotEmpty() && confirmPassword != password) {
                        Text("Passwords do not match")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val uid = selectedUserID as? Int
                    if (uid != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val userPhone = patientViewModel.getPhoneNumberByUserId(uid)
                            withContext(Dispatchers.Main) {
                                if (userPhone == null || userPhone != phoneNumber) {
                                    Toast.makeText(context, "Incorrect phone number", Toast.LENGTH_LONG).show()
                                    return@withContext
                                }

                                if (password.isEmpty() || password.length < 6) {
                                    Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_LONG).show()
                                    return@withContext
                                }

                                if (password != confirmPassword) {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                                    return@withContext
                                }

                                // All validations passed
                                Toast.makeText(context, "Register Successful", Toast.LENGTH_LONG).show()
                                userLoginViewModel.updatePassword(uid, password)
                                navController.navigate("login")
                            }
                        }
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