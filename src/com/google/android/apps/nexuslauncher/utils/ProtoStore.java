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

package com.google.android.apps.nexuslauncher.utils;

import android.content.Context;
import android.util.Log;

import com.android.launcher3.util.IOUtils;
import com.google.protobuf.nano.MessageNano;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ProtoStore {
    private final Context mContext;

    public ProtoStore(Context context) {
        mContext = context.getApplicationContext();
    }

    public void dw(MessageNano p1, String name) {
        try {
            FileOutputStream fos = mContext.openFileOutput(name, 0);
            if (p1 != null) {
                fos.write(MessageNano.toByteArray(p1));
            } else {
                Log.d("ProtoStore", "deleting " + name);
                mContext.deleteFile(name);
            }
        } catch (FileNotFoundException e) {
            Log.d("ProtoStore", "file does not exist " + name);
        } catch (Exception e) {
            Log.e("ProtoStore", "unable to write file " + name, e);
        }
    }

    public boolean dv(String name, MessageNano a) {
        File fileStreamPath = mContext.getFileStreamPath(name);
        try {
            MessageNano.mergeFrom(a, IOUtils.toByteArray(fileStreamPath));
            return true;
        }
        catch (FileNotFoundException ex2) {
            Log.d("ProtoStore", "no cached data");
        }
        catch (Exception ex) {
            Log.e("ProtoStore", "unable to load data", ex);
        }
        return false;
    }
}
