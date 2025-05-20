package tnoodlecli.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import com.beust.jcommander.JCommander;
import tnoodlecli.commands.ScrambleCommand; // Added import

/**
 * Unit tests for the DrawCommand class.
 */
public class DrawCommandTest {

    @Test
    void testDrawCommand_readsFromStdin() {
        String scrambleString = "R U R' U'";
        String puzzleName = "THREE"; // Corresponds to 3x3x3 cube

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try {
            // Simulate stdin
            System.setIn(new ByteArrayInputStream(scrambleString.getBytes()));

            // Capture stdout
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            // Execute the command
            DrawCommand drawCommand = new DrawCommand();

            // Use JCommander to parse arguments and set the 'puzzle' field.
            // 'scramble' field will remain null as it's not provided.
            JCommander.newBuilder()
                .addObject(drawCommand)
                .build()
                .parse("-p", puzzleName);

            drawCommand.run();

            // Assert the output
            String output = baos.toString();
            assertNotNull(output, "Output should not be null.");
            assertFalse(output.isEmpty(), "Output should not be empty.");
            assertTrue(output.contains("<svg"), "Output should contain '<svg'.");
            assertTrue(output.contains("</svg>"), "Output should contain '</svg>'.");
            assertTrue(output.contains("width="), "Output should contain 'width='.");
            assertTrue(output.contains("height="), "Output should contain 'height='.");
            // Check for potential error messages instead of SVG content
            assertFalse(output.contains("Error:"), "Output should not contain 'Error:'.");

        } finally {
            // Restore System.in and System.out
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}

    @Test
    void testFullPipeWorkflow_scrambleToDraw() {
        String puzzleName = "THREE"; // Corresponds to 3x3x3 cube

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream scrambleBaos = new ByteArrayOutputStream();
        ByteArrayOutputStream drawBaos = new ByteArrayOutputStream();

        try {
            // === ScrambleCommand Phase ===
            System.setOut(new PrintStream(scrambleBaos));

            ScrambleCommand scrambleCommand = new ScrambleCommand();
            JCommander.newBuilder()
                .addObject(scrambleCommand)
                .build()
                .parse("-p", puzzleName, "-c", "1"); // Set puzzle and count to 1
            
            scrambleCommand.run();
            String generatedScramble = scrambleBaos.toString().trim(); // Trim to remove trailing newline

            // Restore System.out before DrawCommand potentially prints errors to console during setup
            System.setOut(originalOut); 

            // Ensure scramble is not empty
            assertNotNull(generatedScramble, "Generated scramble should not be null.");
            assertFalse(generatedScramble.isEmpty(), "Generated scramble should not be empty.");

            // === DrawCommand Phase ===
            System.setIn(new ByteArrayInputStream(generatedScramble.getBytes()));
            System.setOut(new PrintStream(drawBaos)); // Capture DrawCommand output

            DrawCommand drawCommand = new DrawCommand();
            JCommander.newBuilder()
                .addObject(drawCommand)
                .build()
                .parse("-p", puzzleName); // Set puzzle, scramble comes from stdin

            drawCommand.run();
            String svgOutput = drawBaos.toString();

            // Assert the SVG output
            assertNotNull(svgOutput, "SVG output should not be null.");
            assertFalse(svgOutput.isEmpty(), "SVG output should not be empty.");
            assertTrue(svgOutput.contains("<svg"), "SVG output should contain '<svg'.");
            assertTrue(svgOutput.contains("</svg>"), "SVG output should contain '</svg>'.");
            assertTrue(svgOutput.contains("width="), "SVG output should contain 'width='.");
            assertTrue(svgOutput.contains("height="), "SVG output should contain 'height='.");
            assertFalse(svgOutput.contains("Error:"), "SVG output should not contain 'Error:'.");

        } finally {
            // Restore System.in and System.out
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
