package com.fit2081.fit2081a2.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fit2081.fit2081a2.utils.UserSessionManager
import com.fit2081.fit2081a2.viewmodel.PatientViewModel
import com.fit2081.fit2081a2.viewmodel.UserLoginViewModel

@Composable
fun SettingsScreen(
    userLoginViewModel: UserLoginViewModel,
    patientViewModel: PatientViewModel,
    navController: NavController,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val userId = UserSessionManager.getLoggedInUserId(context)
    val patientName = remember { mutableStateListOf<String>() }
    val phoneNumber = remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        val nameList = userId?.let { patientViewModel.getPatientNameByUserId(it) }
        patientName.clear()
        if (nameList != null) {
            patientName.addAll(nameList.filterNotNull())
        }
        val phoneNum = userId?.let { patientViewModel.getPhoneNumberByUserId(it) }
        if (phoneNum != null) {
            phoneNumber.value = phoneNum
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Account",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ClickableItem(
                icon = Icons.Outlined.Person,
                label = if (patientName.size >= 2) {
                    val first = patientName[0] ?: ""
                    val last = patientName[1] ?: ""
                    "$first $last"
                } else {
                    "Unknown Name"
                },
                onClick = {navController.navigate("settings/username")},
                showArrow = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            ReadOnlyItem(
                icon = Icons.Outlined.Phone,
                label = phoneNumber.value
            )

            Spacer(modifier = Modifier.height(12.dp))


            ReadOnlyItem(
                icon = Icons.Outlined.Info,
                label = "User ID: $userId"
            )

            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Text(
                text = "Other Settings",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

        }
    }
}

@Composable
fun ReadOnlyItem(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun ClickableItem(
    icon: ImageVector,
    label: String,
    onClick: (() -> Unit)? = null,
    showArrow: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = label,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        if (showArrow) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
