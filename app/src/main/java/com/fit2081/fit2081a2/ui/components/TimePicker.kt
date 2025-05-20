package com.fit2081.fit2081a2.ui.components

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalTime

@Composable
fun TimePicker(
    initialTime: LocalTime? = null,
    show: Boolean,
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val activity = context.findActivity()

    // 使用一次性 effect 来响应 show 状态变化
    LaunchedEffect(show) {
        if (show && activity != null) {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(initialTime?.hour ?: 0)
                .setMinute(initialTime?.minute ?: 0)
                .setTitleText("Select Time")
                .build()

            picker.addOnPositiveButtonClickListener {
                val hour = picker.hour
                val minute = picker.minute
                onTimeSelected(LocalTime.of(hour, minute))
                onDismissRequest()
            }

            picker.addOnDismissListener {
                onDismissRequest()
            }

            // 安全地显示 picker
            picker.show(activity.supportFragmentManager, "timePicker")
        }
    }
}

fun Context.findActivity(): FragmentActivity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is FragmentActivity) return ctx
        ctx = ctx.baseContext
    }
    return null
}


