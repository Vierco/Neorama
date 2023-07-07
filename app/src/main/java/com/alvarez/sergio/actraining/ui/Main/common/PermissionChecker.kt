package com.alvarez.sergio.actraining.ui.Main.common

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionChecker(private val activity: Activity, private val permission: String) {

    fun checkPermission(): Boolean = ContextCompat.checkSelfPermission(
        activity,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}
