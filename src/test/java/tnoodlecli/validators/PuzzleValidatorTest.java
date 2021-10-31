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
package tnoodlecli.validators;

import org.junit.*;

import static org.junit.Assert.*;

import com.beust.jcommander.ParameterException;

public class PuzzleValidatorTest {

    @Test
    public void test_validate() {
        PuzzleValidator validator = new PuzzleValidator();
        for (String puzzleType : PuzzleValidator.puzzles) {
            // Check to make sure no exceptions are thrown
            validator.validate("--puzzle", puzzleType);
            validator.validate("--puzzle", puzzleType.toUpperCase());
        }
    }

    @Test
    public void test_validate_rejections() {
        PuzzleValidator validator = new PuzzleValidator();
        assertThrows(ParameterException.class, () -> validator.validate("--puzzle", "cookies"));
    }

}