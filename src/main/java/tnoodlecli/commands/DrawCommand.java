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
package tnoodlecli.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import org.worldcubeassociation.tnoodle.scrambles.InvalidScrambleException;
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry;
import org.worldcubeassociation.tnoodle.svglite.Svg;

import tnoodlecli.validators.PuzzleValidator;

@Parameters(
    commandNames = "draw",
    commandDescription = "Draws an SVG of the given scramble"
)
public class DrawCommand implements Runnable {

    @Parameter(
        names = { "-p", "--puzzle" },
        description = "Puzzle on which the scramble is applied. Run `tnoodle puzzles` to see the list of options.",
        validateWith = PuzzleValidator.class,
        required = true
    )
    private String puzzle;

    @Parameter(
        names = { "-s", "--scramble" },
        description = "The scramble to draw.",
        required = true
    )
    private String scramble;

    @Parameter(names = { "-o", "--output" }, description = "Output file (.svg)")
    private String output = null;

    public void run() {
        try {
            Svg svg = PuzzleRegistry.valueOf(puzzle.toUpperCase()).getScrambler().drawScramble(scramble, null);
            if (output == null) {
                System.out.println(svg.toString());
            } else {
                File outputFile = new File(output);
                try (PrintWriter writer = new PrintWriter(outputFile)){
                    writer.print(svg.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (InvalidScrambleException e1) {
            e1.printStackTrace();
        } 
    }
}

