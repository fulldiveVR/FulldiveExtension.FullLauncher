
package ch.deletescape.lawnchair.root

import android.content.Context
import ch.deletescape.lawnchair.ensureOnMainThread
import ch.deletescape.lawnchair.lawnchairPrefs
import ch.deletescape.lawnchair.useApplicationContext
import ch.deletescape.lawnchair.util.SingletonHolder
import com.android.launcher3.BuildConfig
import com.topjohnwu.superuser.Shell
import eu.chainfire.librootjava.RootIPCReceiver
import eu.chainfire.librootjava.RootJava
import java.util.*

class RootHelperManager(private val context: Context) {

    private val ipcReceiver = object : RootIPCReceiver<IRootHelper>(null, 0) {
        override fun onConnect(ipc: IRootHelper) {
            rootHelper = ipc
            executeQueuedCommands()
        }

        override fun onDisconnect(ipc: IRootHelper) {
            rootHelper = null
            commandQueue.clear()
        }
    }

    private var commandQueue = LinkedList<(IRootHelper) -> Unit>()
    private var rootHelper: IRootHelper? = null

    init {
        ipcReceiver.setContext(context)
    }

    fun run(command: (IRootHelper) -> Unit) {
        commandQueue.offer(command)
        launchRootHelper()
        executeQueuedCommands()
    }

    private fun launchRootHelper() {
        context.lawnchairPrefs.autoLaunchRoot = isAvailable
        if (!isAvailable) return
        if (rootHelper != null) return

        RootJava.getLaunchScript(
                context, RootHelper::class.java,
                null, null, null, BuildConfig.APPLICATION_ID + ":rootHelper"
                                ).forEach { Shell.su(it).submit() }
    }

    private fun executeQueuedCommands() {
        while (commandQueue.peek() != null && rootHelper != null) {
            commandQueue.poll()(rootHelper!!)
        }
    }

    companion object : SingletonHolder<RootHelperManager, Context>(
            ensureOnMainThread(useApplicationContext(::RootHelperManager))
                                                                  ) {

        val isAvailable by lazy { Shell.rootAccess() }
    }
}
