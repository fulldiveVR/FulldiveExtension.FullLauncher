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

package com.google.android.apps.nexuslauncher.allapps;

import android.content.ComponentName;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import ch.deletescape.lawnchair.predictions.LawnchairEventPredictor;
import ch.deletescape.lawnchair.sesame.Sesame;
import com.android.launcher3.ItemInfo;
import com.android.launcher3.R;
import com.android.launcher3.SecondaryDropTarget;
import com.android.launcher3.logging.LoggerUtils;
import com.android.launcher3.userevent.nano.LauncherLogProto.Target;
import com.android.launcher3.util.ComponentKey;

public class NexusSecondaryDropTarget extends SecondaryDropTarget {

    public static final int DISMISS = R.id.action_dismiss_suggestion;

    public NexusSecondaryDropTarget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NexusSecondaryDropTarget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void setupUi(int action) {
        if (action == mCurrentAccessibilityAction) {
            super.setupUi(action);
            return;
        }

        if (action == DISMISS) {
            mCurrentAccessibilityAction = action;
            mHoverColor = getResources().getColor(R.color.dismiss_target_hover_tint);
            setDrawable(R.drawable.ic_dismiss_no_shadow);
            updateText(R.string.dismiss_drop_target_label);
        } else {
            super.setupUi(action);
        }
    }

    @Override
    public Target getDropTargetForLogging() {
        Target newTarget = LoggerUtils.newTarget(2);
        newTarget.controlType = 5;
        return newTarget;
    }

    @Override
    public boolean supportsAccessibilityDrop(ItemInfo info, View view) {
        if (((ActionsRowView) this.mLauncher.findViewById(R.id.actions_row)).getAction(info) == null) {
            return super.supportsAccessibilityDrop(info, view);
        }
        setupUi(R.id.action_dismiss_suggestion);
        return true;
    }

    @Override
    protected ComponentName performDropAction(View view, ItemInfo info) {
        if (this.mCurrentAccessibilityAction != DISMISS) {
            return super.performDropAction(view, info);
        }

        Action action = ((ActionsRowView) mLauncher.findViewById(R.id.actions_row)).getAction(info);
        LawnchairEventPredictor eventPredictor = (LawnchairEventPredictor) mLauncher
                .getUserEventDispatcher();
        if (eventPredictor.isActionsEnabled()) {
            eventPredictor.setHiddenAction(action.toString());
            mLauncher.getUserEventDispatcher().updateActions();
        } else {
            ActionsController.get(getContext()).onActionDismissed(action);
        }
        return null;
    }
}
