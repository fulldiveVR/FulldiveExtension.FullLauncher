package ch.deletescape.lawnchair

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class FakeLauncher : Activity()

fun changeDefaultHome(context: Context) {
    val pm = context.packageManager
    val fakeLauncher = ComponentName(context, FakeLauncher::class.java)

    pm.setComponentEnabledSetting(fakeLauncher, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                  PackageManager.DONT_KILL_APP)

    val picker = Intent(Intent.ACTION_MAIN)
    picker.addCategory(Intent.CATEGORY_HOME)
    picker.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(picker)

    pm.setComponentEnabledSetting(fakeLauncher, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                                  PackageManager.DONT_KILL_APP)
}

fun getCurrentLauncherPackageName(context: Context): String {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    val resolveInfo = context.packageManager.resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
                                                            )
    return resolveInfo?.activityInfo?.packageName ?: ""
}