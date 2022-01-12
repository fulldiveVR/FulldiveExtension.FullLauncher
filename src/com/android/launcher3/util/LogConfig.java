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

/**
 * This is a utility class that keeps track of all the tag that can be enabled to debug
 * a behavior in runtime.
 *
 * To use any of the strings defined in this class, execute the following command.
 *
 * $ adb shell setprop log.tag.TAGNAME VERBOSE
 */

public class LogConfig {
    // These are list of strings that can be used to replace TAGNAME.

    /**
     * After this tag is turned on, whenever there is n user event, debug information is
     * printed out to logcat.
     */
    public static final String USEREVENT = "UserEvent";

    /**
     * When turned on, all icons are kept on the home screen, even if they don't have an active
     * session.
     */
    public static final String KEEP_ALL_ICONS = "KeepAllIcons";

    /**
     * When turned on, icon cache is only fetched from memory and not disk.
     */
    public static final String MEMORY_ONLY_ICON_CACHE = "MemoryOnlyIconCache";
}
