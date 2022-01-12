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

package com.android.launcher3.model;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.launcher3.ItemInfo;
import com.android.launcher3.LauncherAppWidgetInfo;
import com.android.launcher3.ShortcutInfo;
import com.android.launcher3.compat.PackageInstallerCompat;
import com.android.launcher3.compat.PackageInstallerCompat.PackageInstallInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link PackageInstallStateChangedTask}
 */
@RunWith(AndroidJUnit4.class)
public class PackageInstallStateChangedTaskTest extends BaseModelUpdateTaskTestCase {

    @Before
    public void initData() throws Exception {
        initializeData("package_install_state_change_task_data");
    }

    private PackageInstallStateChangedTask newTask(String pkg, int progress) {
        int state = PackageInstallerCompat.STATUS_INSTALLING;
        PackageInstallInfo installInfo = new PackageInstallInfo(pkg, state, progress);
        return new PackageInstallStateChangedTask(installInfo);
    }

    @Test
    public void testSessionUpdate_ignore_installed() throws Exception {
        executeTaskForTest(newTask("app1", 30));

        // No shortcuts were updated
        verifyProgressUpdate(0);
    }

    @Test
    public void testSessionUpdate_shortcuts_updated() throws Exception {
        executeTaskForTest(newTask("app3", 30));

        verifyProgressUpdate(30, 5L, 6L, 7L);
    }

    @Test
    public void testSessionUpdate_widgets_updated() throws Exception {
        executeTaskForTest(newTask("app4", 30));

        verifyProgressUpdate(30, 8L, 9L);
    }

    private void verifyProgressUpdate(int progress, Long... idsUpdated) {
        HashSet<Long> updates = new HashSet<>(Arrays.asList(idsUpdated));
        for (ItemInfo info : bgDataModel.itemsIdMap) {
            if (info instanceof ShortcutInfo) {
                assertEquals(updates.contains(info.id) ? progress: 0,
                        ((ShortcutInfo) info).getInstallProgress());
            } else {
                assertEquals(updates.contains(info.id) ? progress: -1,
                        ((LauncherAppWidgetInfo) info).installProgress);
            }
        }
    }
}
