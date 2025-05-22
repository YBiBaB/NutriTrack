package com.fit2081.fit2081a2.ui.screens.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.utils.UserSessionManager
import com.fit2081.fit2081a2.viewmodel.UserLoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    userLoginViewModel: UserLoginViewModel,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val userId = UserSessionManager.getLoggedInUserId(context)
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
        ,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Old password",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = oldPassword,
            onValueChange = {
                oldPassword = it
            },
            label = { Text("Enter your original password") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "New Password",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
            },
            label = { Text("Enter your new password") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Confirm Password",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
            },
            label = { Text("Confirm your password") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = {
                    if (userId != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                if (newPassword.isEmpty() || newPassword.length < 6) {
                                    Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_LONG).show()
                                    return@withContext
                                }
                                if (newPassword != confirmPassword) {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                                    return@withContext
                                }
                            }
                            userLoginViewModel.login(userId, oldPassword) { isSuccess ->
                                if (isSuccess) {
                                    userLoginViewModel.updatePassword(userId, newPassword)
                                    Toast.makeText(context, "Reset Successful", Toast.LENGTH_LONG).show()
                                    navController.navigate("settings/main")
                                } else {
                                    Toast.makeText(context, "Incorrect password", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F29BD)
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    "Save",
                    color = Color.White,
                    fontSize = 22.sp,
                )
            }

            OutlinedButton(
                onClick = {
                    navController.navigate("settings/main")
                },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    "Cancel",
                    fontSize = 22.sp,
                )
            }
        }
    }
}