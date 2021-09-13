

package ch.deletescape.lawnchair.groups

import android.view.ViewGroup
import com.android.launcher3.Launcher
import com.android.launcher3.R
import com.android.launcher3.folder.FolderIcon

class DrawerFolderItem(private val info: DrawerFolderInfo, private val index: Int) {

    private var icon: FolderIcon? = null

    fun getFolderIcon(launcher: Launcher, container: ViewGroup): FolderIcon {
        if (icon == null) {
            icon = FolderIcon.fromXml(R.layout.all_apps_folder_icon, launcher, container, info)
        }
        return icon!!.apply {
            (parent as? ViewGroup)?.removeView(this)
        }
    }
}
