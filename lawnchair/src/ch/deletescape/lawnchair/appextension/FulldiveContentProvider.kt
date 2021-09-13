package ch.deletescape.lawnchair.appextension

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import ch.deletescape.lawnchair.changeDefaultHome
import ch.deletescape.lawnchair.settings.ui.SettingsActivity
import java.util.*

class FulldiveContentProvider : ContentProvider() {

	override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
		return when (method.toLowerCase(Locale.ENGLISH)) {
			AppExtensionWorkType.START.id -> {
				Log.d("TestB", "AppExtensionWorkType.START.id $")
				val context = context
				if (context != null) {
					changeDefaultHome(context)
					//todo
//					val startIntent = Intent(context, MainActivity::class.java)
//					startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//					context.startActivity(startIntent)
				}
				null
			}
			AppExtensionWorkType.STOP.id -> {
				Log.d("TestB", "AppExtensionWorkType.START.id $")
				val context = context
				if (context != null) {
					changeDefaultHome(context)
					//todo
					//					val startIntent = Intent(context, MainActivity::class.java)
					//					startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
					//					context.startActivity(startIntent)
				}
				null
			}

			AppExtensionWorkType.OPEN.id -> {
				Log.d("TestB", "AppExtensionWorkType.OPEN.id $")
				val context = context
				if (context != null) {
					//todo
					val startIntent = Intent(context, SettingsActivity::class.java)
					startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
					context.startActivity(startIntent)
				}
				null
			}
			AppExtensionWorkType.GetPermissionsRequired.id -> {
				val bundle = Bundle()
				bundle.putBoolean(ExtensionUtils.KEY_RESULT, false)
				bundle
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

	companion object {
		private const val PREFERENCE_AUTHORITY =
			"com.fulldive.extension.diveforreddit.FulldiveContentProvider"
		const val BASE_URL = "content://$PREFERENCE_AUTHORITY"
	}
}
