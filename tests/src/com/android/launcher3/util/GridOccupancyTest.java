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

package com.android.launcher3.util;

import androidx.test.filters.SmallTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link GridOccupancy}
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class GridOccupancyTest {

    @Test
    public void testFindVacantCell() {
        GridOccupancy grid = initGrid(4,
                1, 1, 1, 0, 0,
                0, 0, 1, 1, 0,
                0, 0, 0, 0, 0,
                1, 1, 0, 0, 0
                );

        int[] vacant = new int[2];
        assertTrue(grid.findVacantCell(vacant, 2, 2));
        assertEquals(vacant[0], 0);
        assertEquals(vacant[1], 1);

        assertTrue(grid.findVacantCell(vacant, 3, 2));
        assertEquals(vacant[0], 2);
        assertEquals(vacant[1], 2);

        assertFalse(grid.findVacantCell(vacant, 3, 3));
    }

    @Test
    public void testIsRegionVacant() {
        GridOccupancy grid = initGrid(4,
                1, 1, 1, 0, 0,
                0, 0, 1, 1, 0,
                0, 0, 0, 0, 0,
                1, 1, 0, 0, 0
        );

        assertTrue(grid.isRegionVacant(4, 0, 1, 4));
        assertTrue(grid.isRegionVacant(0, 1, 2, 2));
        assertTrue(grid.isRegionVacant(2, 2, 3, 2));

        assertFalse(grid.isRegionVacant(3, 0, 2, 4));
        assertFalse(grid.isRegionVacant(0, 0, 2, 1));
    }

    private GridOccupancy initGrid(int rows, int... cells) {
        int cols = cells.length / rows;
        int i = 0;
        GridOccupancy grid = new GridOccupancy(cols, rows);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                grid.cells[x][y] = cells[i] != 0;
                i++;
            }
        }
        return grid;
    }
}
