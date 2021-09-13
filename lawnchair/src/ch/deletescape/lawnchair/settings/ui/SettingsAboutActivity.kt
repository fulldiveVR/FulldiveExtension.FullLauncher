package ch.deletescape.lawnchair.settings.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.launcher3.BuildConfig
import com.android.launcher3.R
import me.jfenn.attribouter.Attribouter

class SettingsAboutActivity : SettingsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun createLaunchFragment(intent: Intent): Fragment {
        return Attribouter.from(this).withGitHubToken(BuildConfig.GITHUB_TOKEN).withFile(R.xml.attribouter).toFragment()
    }

    override fun shouldUseLargeTitle(): Boolean {
        return false
    }

    override fun shouldShowSearch(): Boolean {
        return false
    }
}
