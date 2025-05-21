package com.fit2081.fit2081a2.ui.screens.settings

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.utils.UserSessionManager
import com.fit2081.fit2081a2.viewmodel.PatientViewModel

@Composable
fun EditNameScreen(
    navController: NavController,
    patientViewModel: PatientViewModel,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val userId = UserSessionManager.getLoggedInUserId(context)
    val patientName = remember { mutableStateListOf<String>() }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var firstName = remember { mutableStateOf(patientName.getOrNull(0) ?: "") }
    var lastName = remember { mutableStateOf(patientName.getOrNull(1) ?: "") }


    LaunchedEffect(userId) {
        val nameList = userId?.let { patientViewModel.getPatientNameByUserId(it) }
        patientName.clear()
        if (nameList != null) {
            patientName.addAll(nameList.filterNotNull())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "First name",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = if (firstName != null) firstName.value else "",
            onValueChange = {
                firstName.value = it
            },
            label = { Text("Enter your first name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Last name",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = if (lastName != null) lastName.value else "",
            onValueChange = {
                lastName.value = it
            },
            label = { Text("Enter your last name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = {
                    errorMessage = null
                    if (userId != null && firstName != null && lastName != null) {
                        patientViewModel.updatePatientName(userId, firstName.value, lastName.value)
                        navController.navigate("settings/main")
                    } else {
                        errorMessage = "First name and last name cannot be empty"
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F29BD)),
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
