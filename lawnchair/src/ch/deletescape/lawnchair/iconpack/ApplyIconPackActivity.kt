
package ch.deletescape.lawnchair.iconpack
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import ch.deletescape.lawnchair.theme.ThemeManager
import ch.deletescape.lawnchair.theme.ThemeOverride
import com.android.launcher3.*

class ApplyIconPackActivity : Activity() {
    private val prefs by lazy { Utilities.getLawnchairPrefs(this) }
    private val themeSet: ThemeOverride.ThemeSet get() = ThemeOverride.SettingsTransparent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeOverride(themeSet, this).applyTheme(this)

        val packageName = intent.getStringExtra("packageName")
        prefs.iconPacks.remove(packageName)
        prefs.iconPacks.add(0, packageName)
        val packName = IconPackManager.getInstance(this).packList.currentPack().displayName
        val message = String.format(getString(R.string.icon_pack_applied_toast), packName)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
        Utilities.goToHome(this)
    }
}
