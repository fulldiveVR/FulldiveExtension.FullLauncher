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

package com.google.android.apps.nexuslauncher.smartspace;

public class SmartspaceDataContainer {
    public SmartspaceCard dO;
    public SmartspaceCard dP;

    public SmartspaceDataContainer() {
        dO = null;
        dP = null;
    }

    public boolean isWeatherAvailable() {
        return dO != null;
    }

    public boolean cS() {
        return dP != null;
    }

    public long cT() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (cS() && isWeatherAvailable()) {
            return Math.min(dP.cF(), dO.cF()) - currentTimeMillis;
        }
        if (cS()) {
            return dP.cF() - currentTimeMillis;
        }
        if (isWeatherAvailable()) {
            return dO.cF() - currentTimeMillis;
        }
        return 0L;
    }

    public boolean cU() {
        final boolean b = true;
        boolean b2 = false;
        if (isWeatherAvailable() && dO.cM()) {
            dO = null;
            b2 = b;
        }
        if (cS() && dP.cM()) {
            dP = null;
            b2 = b;
        }
        return b2;
    }

    public String toString() {
        return "{" + dP + "," + dO + "}";
    }
}
