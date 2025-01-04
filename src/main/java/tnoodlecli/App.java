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

import java.util.HashMap;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.JCommander.Builder;

import tnoodlecli.commands.DrawCommand;
import tnoodlecli.commands.PuzzlesCommand;
import tnoodlecli.commands.ScrambleCommand;

public class App {

    @Parameter(names = { "-h", "--help" }, help = true)
    private boolean help;

    @Parameter(names = { "-v", "--version" })
    private boolean version;

    private static HashMap<String, Runnable> commands;

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put("draw", new DrawCommand());
        commands.put("puzzles", new PuzzlesCommand());
        commands.put("scramble", new ScrambleCommand());
    }

    private static JCommander initCLI(App app) {
        Builder jc = JCommander.newBuilder();
        jc.addObject(app);
        for (Runnable command : commands.values()) {
            jc.addCommand(command);
        }
        return jc.build();
    }

    private static void validateArgs(JCommander jc, String[] args) {
        try {
            jc.parse(args);
        } catch (ParameterException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }
    }

    private static void executeCommand(JCommander jc) {
        String commandArg = jc.getParsedCommand();
        commandArg = commandArg == null ? "" : commandArg;
        Runnable command = commands.get(commandArg.toLowerCase());
        if (command == null) {
            jc.usage();
        } else {
            command.run();
        }
    }

    public static void main(String[] args) {
        initCommands();
        App app = new App();
        JCommander jc = initCLI(app);
        
        validateArgs(jc, args);
        
        if (app.version) {
            System.out.println("tnoodle-cli version 1.1.1");
        } else {
            executeCommand(jc);
        }
    }
}
