package com.android.launcher3.appextension

import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import ch.deletescape.lawnchair.settings.ui.SettingsActivity
import java.util.*
import android.content.Intent

class FulldiveContentProvider : ContentProvider() {

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        return when (method.toLowerCase(Locale.ENGLISH)) {
            AppExtensionWorkType.OPEN.id -> {
                val startIntent = Intent(context, SettingsActivity::class.java)
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(startIntent)
                null
            }
            AppExtensionWorkType.GetStatus.id -> {
                val status = when (getCurrentLauncherPackageName()) {
                    FULL_LAUNCHER_PACKAGE_NAME -> AppExtensionState.START.id
                    else -> AppExtensionState.STOP.id
                }
                bundleOf(KEY_WORK_STATUS to status)
            }
            AppExtensionWorkType.GetPermissionsRequired.id -> {
                bundleOf(KEY_RESULT to false)
            }
            else -> {
                super.call(method, arg, extras)
            }
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun getType(uri: Uri): String? = null

    override fun query(
            uri: Uri,
            projection: Array<String>?,
            selection: String?,
            selectionArgs: Array<String>?,
            sortOrder: String?
                      ): Cursor? = null

    override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<String>?
                       ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    private fun getCurrentLauncherPackageName(): String {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        val resolveInfo = context?.packageManager?.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
                                                                  )
        return resolveInfo?.activityInfo?.packageName ?: ""
    }

    companion object {
        private const val PREFERENCE_AUTHORITY =
                "com.fulldive.extension.launcher.FulldiveContentProvider"
        const val BASE_URL = "content://$PREFERENCE_AUTHORITY"
        const val FULL_LAUNCHER_PACKAGE_NAME = "com.android.launcher3.appextension"
        const val KEY_WORK_STATUS = "work_status"
        const val KEY_RESULT = "result"
    }
}