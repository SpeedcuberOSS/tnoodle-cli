/**
 * TNoodle CLI - Generate WCA Scrambles on the Command Line
 * Copyright (C) 2021  Joseph Hale
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tnoodlecli;

import org.junit.*;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void test_example() {
        assertEquals(true, true);
    }

    @Test
    public void test_help() {
        String[] args = {"--help"};
        App.main(args);
    }

    @Test
    public void test_version() {
        String[] args = {"--version"};
        App.main(args);
    }

    @Test
    public void test_scrambleCommand() {
        String[] args = {"scramble", "--puzzle", "three"};
        App.main(args);
    }

    @Test
    public void test_drawCommand() {
        String[] args = {"draw", "--puzzle", "three", "--scramble", "R U R' U'"};
        App.main(args);
    }

}