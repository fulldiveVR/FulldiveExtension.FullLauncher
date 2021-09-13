
package ch.deletescape.lawnchair.customnavbar.preferences

import android.content.Context
import android.util.AttributeSet
import ch.deletescape.lawnchair.BlankActivity
import ch.deletescape.lawnchair.LawnchairLauncher
import ch.deletescape.lawnchair.customnavbar.CustomNavBar
import ch.deletescape.lawnchair.preferences.ResumablePreference
import ch.deletescape.lawnchair.preferences.StyledSwitchPreferenceCompat
import com.android.launcher3.R
import com.android.launcher3.util.PackageManagerHelper

class CustomNavBarIntegrationPreference(context: Context, attrs: AttributeSet?) :
        StyledSwitchPreferenceCompat(context, attrs), ResumablePreference {

    private val manager = CustomNavBar.getInstance(context)
    private val isInstalled = manager.isInstalled

    init {
        isPersistent = false
        updateSummary()
        super.setChecked(manager.enableIntegration)
    }

    private fun updateSummary() {
        summary = context.getString(when {
            isInstalled -> R.string.customnavbar_back_hiding_desc
            else -> R.string.customnavbar_install
        })
    }

    override fun onResume() {
        updateSummary()
    }

    override fun onClick() {
        if (isInstalled) {
            if (manager.isPermissionGranted) {
                super.onClick()
            } else {
                BlankActivity.requestPermission(
                        context, CustomNavBar.MODIFY_NAVBAR_PERMISSION,
                        LawnchairLauncher.REQUEST_PERMISSION_MODIFY_NAVBAR) {
                    if (it) {
                        super.onClick()
                    }
                }
            }
        } else {
            context.startActivity(PackageManagerHelper(context).getMarketIntent(CustomNavBar.PACKAGE))
        }
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        manager.enableIntegration = checked
    }
}
