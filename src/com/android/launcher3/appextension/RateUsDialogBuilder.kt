package com.android.launcher3.appextension

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import com.android.launcher3.R
import kotlin.math.roundToInt

object RateUsDialogBuilder {

	fun show(context: Context, onPositiveClicked: (Int) -> Unit) {
		val view = LayoutInflater.from(context).inflate(R.layout.rate_us_dialog_layout, null)
		val ratingBar = view.findViewById<AppCompatRatingBar>(R.id.ratingBar)
		var ratingValue = 0
		ratingBar.setOnRatingBarChangeListener { _, value, fromUser ->
			if (fromUser) {
				ratingValue = value.roundToInt()
			}
		}

		val dialog = AlertDialog.Builder(context)
			.setView(view)
			.setTitle(R.string.rate_us_title)
			.setPositiveButton(R.string.rate_submit) { _, _ ->
				onPositiveClicked.invoke(ratingValue)
			}
			.setNegativeButton(R.string.rate_cancel) { _, _ -> }
			.create()

		dialog.setOnShowListener {
			dialog.getButton(AlertDialog.BUTTON_POSITIVE)
				?.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
			dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
				?.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary))
		}
		dialog.show()
	}
}