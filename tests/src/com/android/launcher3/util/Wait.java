/*
 *     Copyright (C) 2021 Lawnchair Team.
 *
 *     This file is part of Lawnchair Launcher.
 *
 *     Lawnchair Launcher is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Lawnchair Launcher is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Lawnchair Launcher.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.android.launcher3.util;

import android.os.SystemClock;

/**
 * A utility class for waiting for a condition to be true.
 */
public class Wait {

    private static final long DEFAULT_SLEEP_MS = 200;

    public static boolean atMost(Condition condition, long timeout) {
        return atMost(condition, timeout, DEFAULT_SLEEP_MS);
    }

    public static boolean atMost(Condition condition, long timeout, long sleepMillis) {
        long endTime = SystemClock.uptimeMillis() + timeout;
        while (SystemClock.uptimeMillis() < endTime) {
            try {
                if (condition.isTrue()) {
                    return true;
                }
            } catch (Throwable t) {
                // Ignore
            }
            SystemClock.sleep(sleepMillis);
        }

        // Check once more before returning false.
        try {
            if (condition.isTrue()) {
                return true;
            }
        } catch (Throwable t) {
            // Ignore
        }
        return false;
    }
}
