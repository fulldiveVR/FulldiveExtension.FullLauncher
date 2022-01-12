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

package com.android.launcher3.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.android.launcher3.Utilities;

/**
 * A {@link ViewOutlineProvider} that has helper functions to create reveal animations.
 * This class should be extended so that subclasses can define the reveal shape as the
 * animation progresses from 0 to 1.
 */
public abstract class RevealOutlineAnimation extends ViewOutlineProvider {
    protected Rect mOutline;
    protected float mOutlineRadius;

    public RevealOutlineAnimation() {
        mOutline = new Rect();
    }

    /** Returns whether elevation should be removed for the duration of the reveal animation. */
    abstract boolean shouldRemoveElevationDuringAnimation();
    /** Sets the progress, from 0 to 1, of the reveal animation. */
    abstract void setProgress(float progress);

    public ValueAnimator createRevealAnimator(final View revealView, boolean isReversed) {
        ValueAnimator va =
                isReversed ? ValueAnimator.ofFloat(1f, 0f) : ValueAnimator.ofFloat(0f, 1f);
        final float elevation = revealView.getElevation();

        va.addListener(new AnimatorListenerAdapter() {
            private boolean mIsClippedToOutline;
            private ViewOutlineProvider mOldOutlineProvider;

            public void onAnimationStart(Animator animation) {
                mIsClippedToOutline = revealView.getClipToOutline();
                mOldOutlineProvider = revealView.getOutlineProvider();

                revealView.setOutlineProvider(RevealOutlineAnimation.this);
                revealView.setClipToOutline(true);
                if (shouldRemoveElevationDuringAnimation()) {
                    revealView.setTranslationZ(-elevation);
                }
            }

            public void onAnimationEnd(Animator animation) {
                revealView.setOutlineProvider(mOldOutlineProvider);
                revealView.setClipToOutline(mIsClippedToOutline);
                if (shouldRemoveElevationDuringAnimation()) {
                    revealView.setTranslationZ(0);
                }
            }

        });

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                float progress = (Float) arg0.getAnimatedValue();
                setProgress(progress);
                revealView.invalidateOutline();
                if (!Utilities.ATLEAST_LOLLIPOP_MR1) {
                    revealView.invalidate();
                }
            }
        });
        return va;
    }

    @Override
    public void getOutline(View v, Outline outline) {
        outline.setRoundRect(mOutline, mOutlineRadius);
    }

    public float getRadius() {
        return mOutlineRadius;
    }

    public void getOutline(Rect out) {
        out.set(mOutline);
    }
}
