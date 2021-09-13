
package ch.deletescape.lawnchair.root;

import android.hardware.input.InputManager;
import android.os.PowerManager;
import android.os.SystemClock;
import androidx.core.content.ContextCompat;
import android.view.KeyEvent;
import eu.chainfire.librootjava.RootJava;

public class RootHelperUtils {

    private static PowerManager getPowerManager() {
        return ContextCompat.getSystemService(RootJava.getSystemContext(), PowerManager.class);
    }

    public static void goToSleep() {
        getPowerManager().goToSleep(SystemClock.uptimeMillis());
    }

    public static void injectInputEvent(KeyEvent ev) {
        InputManager.getInstance().injectInputEvent(ev, InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);
    }
}
