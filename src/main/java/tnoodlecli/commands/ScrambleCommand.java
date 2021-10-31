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

import org.json.JSONArray;
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry;

import tnoodlecli.validators.PuzzleValidator;

@Parameters(
    commandNames = "scramble",
    commandDescription = "Generate a random scramble"
)
public class ScrambleCommand implements Runnable {

    @Parameter(
        names = { "-p", "--puzzle" },
        description = "Puzzle to generate scramble for. Run `tnoodle puzzles` to see the list of options.",
        validateWith = PuzzleValidator.class
    )
    private String puzzle = "three";

    @Parameter(names = { "-c", "--count" }, description = "Number of scrambles to generate")
    private int count = 1;

    @Parameter(names = { "-o", "--output" }, description = "Output file (.json)")
    private String output = null;

    public void run() {
        String[] scrambles = PuzzleRegistry.valueOf(puzzle.toUpperCase()).getScrambler().generateScrambles(count);
        if (output == null) {
            for (String scramble : scrambles) {
                System.out.println(scramble);
            }
        } else {
            JSONArray scramblesJson = new JSONArray();
            for (String scramble : scrambles) {
                scramblesJson.put(scramble);
            }
            File outputFile = new File(output);
            try (PrintWriter writer = new PrintWriter(outputFile)){
                writer.print(scramblesJson.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

