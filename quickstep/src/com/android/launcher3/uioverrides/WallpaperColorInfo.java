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

package com.android.launcher3.uioverrides;

import android.content.Context;
import com.android.launcher3.Utilities;

public abstract class WallpaperColorInfo {

    private static final Object sInstanceLock = new Object();
    private static WallpaperColorInfo sInstance;

    public static WallpaperColorInfo getInstance(Context context) {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
//                if (Utilities.ATLEAST_Q && !Utilities.HIDDEN_APIS_ALLOWED) {
//                    sInstance = new WallpaperColorInfoVL(context.getApplicationContext());
//                } else if (Utilities.ATLEAST_OREO_MR1) {
//                    sInstance = new WallpaperColorInfoVOMR1(context.getApplicationContext());
//                } else {
                    sInstance = new WallpaperColorInfoVL(context.getApplicationContext());
//                }
            }
            return sInstance;
        }
    }

    public abstract int getMainColor();

    public abstract int getActualMainColor();

    public abstract int getSecondaryColor();

    public abstract int getActualSecondaryColor();

    public abstract int getTertiaryColor();

    public abstract boolean isDark();

    public abstract boolean supportsDarkText();

    public abstract void addOnChangeListener(OnChangeListener listener);

    public abstract void removeOnChangeListener(OnChangeListener listener);

    public interface OnChangeListener {
        void onExtractedColorsChanged(WallpaperColorInfo wallpaperColorInfo);
    }
}
