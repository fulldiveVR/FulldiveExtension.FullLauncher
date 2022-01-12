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

package com.android.launcher3.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.filters.MediumTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.launcher3.LauncherProvider.DatabaseHelper;
import com.android.launcher3.LauncherSettings.Favorites;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link RestoreDbTask}
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class RestoreDbTaskTest {

    @Test
    public void testGetProfileId() throws Exception {
        SQLiteDatabase db = new MyDatabaseHelper(23).getWritableDatabase();
        assertEquals(23, new RestoreDbTask().getDefaultProfileId(db));
    }

    @Test
    public void testMigrateProfileId() throws Exception {
        SQLiteDatabase db = new MyDatabaseHelper(42).getWritableDatabase();
        // Add some dummy data
        for (int i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            values.put(Favorites._ID, i);
            values.put(Favorites.TITLE, "item " + i);
            db.insert(Favorites.TABLE_NAME, null, values);
        }
        // Verify item add
        assertEquals(5, getCount(db, "select * from favorites where profileId = 42"));

        new RestoreDbTask().migrateProfileId(db, 33);

        // verify data migrated
        assertEquals(0, getCount(db, "select * from favorites where profileId = 42"));
        assertEquals(5, getCount(db, "select * from favorites where profileId = 33"));

        // Verify default value changed
        ContentValues values = new ContentValues();
        values.put(Favorites._ID, 100);
        values.put(Favorites.TITLE, "item 100");
        db.insert(Favorites.TABLE_NAME, null, values);
        assertEquals(6, getCount(db, "select * from favorites where profileId = 33"));
    }

    private int getCount(SQLiteDatabase db, String sql) {
        try (Cursor c = db.rawQuery(sql, null)) {
            return c.getCount();
        }
    }

    private class MyDatabaseHelper extends DatabaseHelper {

        private final long mProfileId;

        MyDatabaseHelper(long profileId) {
            super(InstrumentationRegistry.getContext(), null, null);
            mProfileId = profileId;
        }

        @Override
        public long getDefaultUserSerial() {
            return mProfileId;
        }

        @Override
        protected void handleOneTimeDataUpgrade(SQLiteDatabase db) { }

        protected void onEmptyDbCreated() { }
    }
}
