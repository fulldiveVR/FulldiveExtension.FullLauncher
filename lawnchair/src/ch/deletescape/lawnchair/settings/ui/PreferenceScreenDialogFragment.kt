

package ch.deletescape.lawnchair.settings.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.deletescape.lawnchair.getThemeAttr
import com.android.launcher3.R

class PreferenceScreenDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_preference_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = arguments!!.getInt(KEY_CONTENT)
        val fragment = SettingsActivity.DialogSettingsFragment.newInstance("", content)
        childFragmentManager.beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(activity!!, activity!!.getThemeAttr(R.attr.alertDialogTheme))
    }

    companion object {

        private const val KEY_THEME = "theme"
        private const val KEY_CONTENT = "content"

        fun newInstance(preference: PreferenceDialogPreference) = PreferenceScreenDialogFragment().apply {
            arguments = Bundle(2).apply {
                putInt(KEY_THEME, preference.context.getThemeAttr(R.attr.alertDialogTheme))
                putInt(KEY_CONTENT, preference.content)
            }
        }
    }
}
