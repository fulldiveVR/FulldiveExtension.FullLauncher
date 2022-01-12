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

import android.animation.PropertyValuesHolder;
import android.view.View;

import java.util.ArrayList;

/**
 * Helper class to build a list of {@link PropertyValuesHolder} for view properties
 */
public class PropertyListBuilder {

    private final ArrayList<PropertyValuesHolder> mProperties = new ArrayList<>();

    public PropertyListBuilder translationX(float value) {
        mProperties.add(PropertyValuesHolder.ofFloat(View.TRANSLATION_X, value));
        return this;
    }

    public PropertyListBuilder translationY(float value) {
        mProperties.add(PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, value));
        return this;
    }

    public PropertyListBuilder scaleX(float value) {
        mProperties.add(PropertyValuesHolder.ofFloat(View.SCALE_X, value));
        return this;
    }

    public PropertyListBuilder scaleY(float value) {
        mProperties.add(PropertyValuesHolder.ofFloat(View.SCALE_Y, value));
        return this;
    }

    /**
     * Helper method to set both scaleX and scaleY
     */
    public PropertyListBuilder scale(float value) {
        return scaleX(value).scaleY(value);
    }

    public PropertyListBuilder alpha(float value) {
        mProperties.add(PropertyValuesHolder.ofFloat(View.ALPHA, value));
        return this;
    }

    public PropertyValuesHolder[] build() {
        return mProperties.toArray(new PropertyValuesHolder[mProperties.size()]);
    }
}
