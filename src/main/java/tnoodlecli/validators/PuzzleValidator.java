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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry;


public class PuzzleValidator implements IParameterValidator {

    public static List<String> puzzles = Arrays.stream(PuzzleRegistry.values())
            .map(PuzzleRegistry::name)
            .map(String::toLowerCase)
            .collect(Collectors.toList());

    public static final String PUZZLE_LIST_STRING = String.join(", ", puzzles);

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!puzzles.contains(value.toLowerCase())) {
            String message = String.format("Puzzle '%s' is not a valid puzzle. Valid puzzles are: %s", value,
                    PUZZLE_LIST_STRING);
            throw new ParameterException(message);
        }
    }

    
}
