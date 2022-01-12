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

package com.android.launcher3;

import android.service.notification.StatusBarNotification;

import com.android.launcher3.notification.NotificationKeyData;
import com.android.launcher3.notification.NotificationListener;
import com.android.launcher3.util.PackageUserKey;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LauncherNotifications implements NotificationListener.NotificationsChangedListener {
    private static LauncherNotifications sInstance;

    public static synchronized LauncherNotifications getInstance() {
        if (sInstance == null) {
            sInstance = new LauncherNotifications();
        }
        return sInstance;
    }

    private final Set<NotificationListener.NotificationsChangedListener> mListeners = new HashSet<>();

    public void addListener(NotificationListener.NotificationsChangedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void onNotificationPosted(PackageUserKey postedPackageUserKey, NotificationKeyData notificationKey, boolean shouldBeFilteredOut) {
        for (NotificationListener.NotificationsChangedListener listener : mListeners) {
            listener.onNotificationPosted(postedPackageUserKey, notificationKey, shouldBeFilteredOut);
        }
    }

    @Override
    public void onNotificationRemoved(PackageUserKey removedPackageUserKey, NotificationKeyData notificationKey) {
        for (NotificationListener.NotificationsChangedListener listener : mListeners) {
            listener.onNotificationRemoved(removedPackageUserKey, notificationKey);
        }
    }

    @Override
    public void onNotificationFullRefresh(List<StatusBarNotification> activeNotifications) {
        for (NotificationListener.NotificationsChangedListener listener : mListeners) {
            listener.onNotificationFullRefresh(activeNotifications);
        }
    }
}
