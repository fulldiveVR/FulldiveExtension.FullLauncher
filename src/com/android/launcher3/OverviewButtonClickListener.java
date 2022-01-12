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

import android.view.View;

import com.android.launcher3.userevent.nano.LauncherLogProto.Action;

/**
 * A specialized listener for Overview buttons where both clicks and long clicks are logged
 * handled the same via {@link #handleViewClick(View)}.
 */
public abstract class OverviewButtonClickListener implements View.OnClickListener,
        View.OnLongClickListener {

    private int mControlType; /** ControlType enum as defined in {@link Action.Touch} */

    public OverviewButtonClickListener(int controlType) {
        mControlType = controlType;
    }

    public void attachTo(View v) {
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (shouldPerformClick(view)) {
            handleViewClick(view, Action.Touch.TAP);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (shouldPerformClick(view)) {
            handleViewClick(view, Action.Touch.LONGPRESS);
        }
        return true;
    }

    private boolean shouldPerformClick(View view) {
        return !Launcher.getLauncher(view.getContext()).getWorkspace().isSwitchingState();
    }

    private void handleViewClick(View view, int action) {
        handleViewClick(view);
        Launcher.getLauncher(view.getContext()).getUserEventDispatcher()
                .logActionOnControl(action, mControlType);
    }

    public abstract void handleViewClick(View view);
}