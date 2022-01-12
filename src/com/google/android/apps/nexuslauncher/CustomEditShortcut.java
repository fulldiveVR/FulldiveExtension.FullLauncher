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

import android.content.Context;
import androidx.annotation.Keep;
import android.view.View;
import ch.deletescape.lawnchair.override.CustomInfoProvider;
import com.android.launcher3.AbstractFloatingView;
import com.android.launcher3.ItemInfo;
import com.android.launcher3.Launcher;
import com.android.launcher3.popup.SystemShortcut;

@Keep
public class CustomEditShortcut extends SystemShortcut.Custom {
    public CustomEditShortcut(Context context) {
        super();
    }

    @Override
    public View.OnClickListener getOnClickListener(final Launcher launcher, final ItemInfo itemInfo) {
        boolean enabled = CustomInfoProvider.Companion.isEditable(itemInfo);
        return enabled ? new View.OnClickListener() {
            private boolean mOpened = false;

            @Override
            public void onClick(View view) {
                if (!mOpened) {
                    mOpened = true;
                    AbstractFloatingView.closeAllOpenViews(launcher);
                    CustomBottomSheet.show(launcher, itemInfo);
                }
            }
        } : null;
    }
}
