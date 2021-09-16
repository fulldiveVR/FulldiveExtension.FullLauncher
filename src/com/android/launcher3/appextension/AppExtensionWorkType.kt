package com.android.launcher3.appextension

sealed class AppExtensionWorkType(val id: String) {
    object START: AppExtensionWorkType("start")
    object STOP: AppExtensionWorkType("stop")
    object OPEN: AppExtensionWorkType("open")
    object GetPermissionsRequired : AppExtensionWorkType("get_permissions_required")
    object GetStatus : AppExtensionWorkType("get_status")
}
