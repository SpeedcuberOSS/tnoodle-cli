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

import tnoodlecli.validators.PuzzleValidator;

@Parameters(
    commandNames = "puzzles",
    commandDescription = "Lists the puzzles this cli supports"
)
public class PuzzlesCommand implements Runnable {

    @Parameter(names = { "-o", "--output" }, description = "Output file (.json)")
    private String output = null;

    public void run() {
        if (output == null) {
            for (String puzzle : PuzzleValidator.puzzles) {
                System.out.println(puzzle);
            }
        } else {
            JSONArray puzzlesJson = new JSONArray();
            for (String puzzle : PuzzleValidator.puzzles) {
                puzzlesJson.put(puzzle);
            }
            File outputFile = new File(output);
            try (PrintWriter writer = new PrintWriter(outputFile)){
                writer.print(puzzlesJson.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

