

package ch.deletescape.lawnchair.root

import com.android.launcher3.BuildConfig
import eu.chainfire.librootjava.RootIPC
import android.view.InputDevice
import android.view.KeyCharacterMap
import android.view.KeyEvent

class RootHelper : IRootHelper.Stub() {

    override fun goToSleep() {
        RootHelperUtils.goToSleep()
    }

    override fun sendKeyEvent(code: Int, action: Int, flags: Int, downTime: Long, eventTime: Long) {
        val repeatCount = if (flags and KeyEvent.FLAG_LONG_PRESS != 0) 1 else 0
        val ev = KeyEvent(downTime, eventTime, action, code, repeatCount,
                0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                flags or KeyEvent.FLAG_FROM_SYSTEM or KeyEvent.FLAG_VIRTUAL_HARD_KEY,
                InputDevice.SOURCE_KEYBOARD)
        RootHelperUtils.injectInputEvent(ev)
    }

    companion object {

        @JvmStatic
        fun main(vararg args: String) {
            try {
                RootIPC(BuildConfig.APPLICATION_ID, RootHelper(), 0, 30 * 1000, true)
            } catch (e: RootIPC.TimeoutException) {
                // a connection wasn't established
            }

        }
    }
}
