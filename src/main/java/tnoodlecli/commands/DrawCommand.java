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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
        required = false
    )
    private String scramble;

    @Parameter(names = { "-o", "--output" }, description = "Output file (.svg)")
    private String output = null;

    public void run() {
        try {
            if (scramble == null || scramble.isEmpty()) {
                try {
                    if (System.in.available() > 0) {
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextLine()) {
                            scramble = scanner.nextLine();
                            if (scramble.trim().isEmpty()) {
                                System.err.println("Error: Received empty scramble from standard input.");
                                System.exit(1);
                            }
                        } else {
                            // This case means stdin had available bytes but nothing was read by nextLine (e.g. only EOF)
                            System.err.println("Error: Standard input was open but no scramble string was provided.");
                            System.exit(1);
                        }
                        // We don't close the scanner for System.in here
                    } else {
                        // System.in.available() <= 0 means no input from stdin when -s is not used
                        System.err.println("Error: No scramble provided via -s/--scramble and no input detected on standard input.");
                        System.exit(1);
                    }
                } catch (IOException e) {
                    // Handle IOException from System.in.available()
                    System.err.println("Error accessing standard input: " + e.getMessage());
                    System.exit(1);
                }
            }

            // This check is now more of a safeguard, as the logic above should handle most empty/null cases.
            if (scramble == null || scramble.trim().isEmpty()) {
                System.err.println("Error: Scramble string not provided or is empty.");
                System.exit(1);
            }

            Svg svg = PuzzleRegistry.valueOf(puzzle.toUpperCase()).getScrambler().drawScramble(scramble, null);
            if (output == null) {
                System.out.println(svg.toString());
            } else {
                File outputFile = new File(output);
                try (PrintWriter writer = new PrintWriter(outputFile)){
                    writer.print(svg.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace(); // Or a more user-friendly message + System.exit(1)
                }
            }
        } catch (InvalidScrambleException e1) {
            System.err.println("Error: Invalid scramble string: " + e1.getMessage());
            // e1.printStackTrace(); // Optionally print stack trace for more detail
            System.exit(1);
        } 
    }
}

