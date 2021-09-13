

package ch.deletescape.lawnchair.override

import android.content.Context
import ch.deletescape.lawnchair.iconpack.IconPackManager
import com.android.launcher3.AppInfo
import com.android.launcher3.FolderInfo
import com.android.launcher3.ItemInfo
import com.android.launcher3.ShortcutInfo

abstract class CustomInfoProvider<in T : ItemInfo>(val context: Context) {

    abstract fun getTitle(info: T): String

    abstract fun getDefaultTitle(info: T): String

    abstract fun getCustomTitle(info: T): String?

    abstract fun setTitle(info: T, title: String?)

    open fun supportsIcon() = true

    abstract fun setIcon(info: T, entry: IconPackManager.CustomIconEntry?)

    abstract fun getIcon(info: T): IconPackManager.CustomIconEntry?

    open fun supportsSwipeUp(info: T) = false

    open fun setSwipeUpAction(info: T, action: String?) {
        TODO("not implemented")
    }

    open fun getSwipeUpAction(info: T): String? {
        TODO("not implemented")
    }

    open fun supportsBadgeVisible(info: T) = false

    open fun setBadgeVisible(info: T, visible: Boolean) {
        TODO("not implemented")
    }

    open fun getBadgeVisible(info: T): Boolean {
        TODO("not implemented")
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun <T : ItemInfo> forItem(context: Context, info: ItemInfo?): CustomInfoProvider<T>? {
            return when (info) {
                is AppInfo -> AppInfoProvider.getInstance(context)
                is ShortcutInfo -> ShortcutInfoProvider.getInstance(context)
                is FolderInfo -> FolderInfoProvider.getInstance(context)
                else -> null
            } as CustomInfoProvider<T>?
        }

        fun isEditable(info: ItemInfo): Boolean {
            return info is AppInfo || (info is ShortcutInfo && !info.hasPromiseIconUi())
                    || info is FolderInfo
        }
    }
}
