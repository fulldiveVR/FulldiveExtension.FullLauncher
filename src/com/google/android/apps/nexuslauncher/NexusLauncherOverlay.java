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

package com.google.android.apps.nexuslauncher;

import com.android.launcher3.Launcher;
import com.android.launcher3.Utilities;
import com.google.android.libraries.gsa.launcherclient.ISerializableScrollCallback;
import com.google.android.libraries.gsa.launcherclient.LauncherClient;

public class NexusLauncherOverlay implements Launcher.LauncherOverlay, ISerializableScrollCallback {
    final static String PREF_PERSIST_FLAGS = "pref_persistent_flags";

    private LauncherClient mClient;
    final Launcher mLauncher;
    private Launcher.LauncherOverlayCallbacks mOverlayCallbacks;
    boolean mFlagsChanged = false;
    private int mFlags;
    boolean mAttached = false;

    public NexusLauncherOverlay(Launcher launcher) {
        mLauncher = launcher;
        mFlags = Utilities.getDevicePrefs(launcher).getInt(PREF_PERSIST_FLAGS, 0);
    }

    public void setClient(LauncherClient client) {
        mClient = client;
    }

    @Override
    public void setPersistentFlags(int flags) {
        flags &= (8 | 16);
        if (flags != mFlags) {
            mFlagsChanged = true;
            mFlags = flags;
            Utilities.getDevicePrefs(mLauncher).edit().putInt(PREF_PERSIST_FLAGS, flags).apply();
        }
    }

    @Override
    public void onServiceStateChanged(boolean overlayAttached) {
        if (overlayAttached != mAttached) {
            mAttached = overlayAttached;
            mLauncher.setLauncherOverlay(overlayAttached ? this : null);
        }
    }

    @Override
    public void onOverlayScrollChanged(float n) {
        if (mOverlayCallbacks != null) {
            mOverlayCallbacks.onScrollChanged(n);
        }
    }

    @Override
    public void onScrollChange(float progress, boolean rtl) {
        mClient.setScroll(progress);
    }

    @Override
    public void onScrollInteractionBegin() {
        mClient.startScroll();
    }

    @Override
    public void onScrollInteractionEnd() {
        mClient.endScroll();
    }

    @Override
    public void setOverlayCallbacks(Launcher.LauncherOverlayCallbacks cb) {
        mOverlayCallbacks = cb;
    }
}
