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

import static com.android.launcher3.LauncherState.NORMAL;
import static com.android.launcher3.LauncherState.OVERVIEW;
import static com.android.quickstep.TouchInteractionService.EDGE_NAV_BAR;

import android.view.MotionEvent;

import com.android.launcher3.AbstractFloatingView;
import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherState;
import com.android.launcher3.LauncherStateManager.AnimationComponents;
import com.android.launcher3.touch.AbstractStateChangeTouchController;
import com.android.launcher3.touch.SwipeDetector;
import com.android.launcher3.userevent.nano.LauncherLogProto;
import com.android.launcher3.userevent.nano.LauncherLogProto.Action.Direction;
import com.android.quickstep.RecentsModel;

/**
 * Touch controller for handling edge swipes in landscape/seascape UI
 */
public class LandscapeEdgeSwipeController extends AbstractStateChangeTouchController {

    private static final String TAG = "LandscapeEdgeSwipeCtrl";

    public LandscapeEdgeSwipeController(Launcher l) {
        super(l, SwipeDetector.HORIZONTAL);
    }

    @Override
    protected boolean canInterceptTouch(MotionEvent ev) {
        if (mCurrentAnimation != null) {
            // If we are already animating from a previous state, we can intercept.
            return true;
        }
        if (AbstractFloatingView.getTopOpenView(mLauncher) != null) {
            return false;
        }
        return mLauncher.isInState(NORMAL) && (ev.getEdgeFlags() & EDGE_NAV_BAR) != 0;
    }

    @Override
    protected LauncherState getTargetState(LauncherState fromState, boolean isDragTowardPositive) {
        boolean draggingFromNav = mLauncher.getDeviceProfile().isSeascape() != isDragTowardPositive;
        return draggingFromNav ? OVERVIEW : NORMAL;
    }

    @Override
    protected int getLogContainerTypeForNormalState() {
        return LauncherLogProto.ContainerType.NAVBAR;
    }

    @Override
    protected float getShiftRange() {
        return mLauncher.getDragLayer().getWidth();
    }

    @Override
    protected float initCurrentAnimation(@AnimationComponents int animComponent) {
        float range = getShiftRange();
        long maxAccuracy = (long) (2 * range);
        mCurrentAnimation = mLauncher.getStateManager().createAnimationToNewWorkspace(mToState,
                maxAccuracy, animComponent);
        return (mLauncher.getDeviceProfile().isSeascape() ? 2 : -2) / range;
    }

    @Override
    protected int getDirectionForLog() {
        return mLauncher.getDeviceProfile().isSeascape() ? Direction.RIGHT : Direction.LEFT;
    }

    @Override
    protected void onSwipeInteractionCompleted(LauncherState targetState, int logAction) {
        super.onSwipeInteractionCompleted(targetState, logAction);
        if (mStartState == NORMAL && targetState == OVERVIEW) {
            RecentsModel.getInstance(mLauncher).onOverviewShown(true, TAG);
        }
    }
}
