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

package com.google.android.apps.nexuslauncher.search;

import com.android.launcher3.allapps.search.AllAppsSearchBarController;
import com.android.launcher3.util.ComponentKey;

import java.util.ArrayList;
import java.util.List;

class SearchResult {
    final AllAppsSearchBarController.Callbacks mCallbacks;
    final String mQuery;
    final ArrayList<ComponentKey> mApps;
    final List<String> mSuggestions;

    SearchResult(String query, AllAppsSearchBarController.Callbacks callbacks) {
        mApps = new ArrayList<>();
        mQuery = query;
        mCallbacks = callbacks;
        mSuggestions = new ArrayList<>();
    }
}
