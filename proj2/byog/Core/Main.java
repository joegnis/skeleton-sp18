package byog.Core;

import byog.TileEngine.TETile;
import org.apache.commons.cli.*;

/**
 * This is the main entry point for the program. This class simply parses
 * the command line inputs, and lets the byog.Core.Game class take over
 * in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("h", "help", false, "print help message");
        Option optionRandomSeed = Option.builder()
                .hasArg()
                .type(Integer.class)
                .longOpt("random-seed")
                .build();
        options.addOption(optionRandomSeed);
        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        try {
            line = parser.parse(options, args);
            if (line.hasOption("help")) {
                new HelpFormatter().printHelp("proj2", options);
                System.exit(0);
            }

            String[] arguments = line.getArgs();
            if (arguments.length > 1) {
                System.out.println("Can only have one argument - the input string");
                System.exit(0);
            } else {
                Game game;
                if (line.hasOption(optionRandomSeed)) {
                    game = new Game(Integer.parseInt(line.getOptionValue(optionRandomSeed)));
                } else {
                    game = new Game();
                }

                if (arguments.length == 1) {
                    TETile[][] worldState = game.playWithInputString(arguments[0]);
                    System.out.println(TETile.toString(worldState));
                } else {
                    game.playWithKeyboard();
                }
            }
        } catch (ParseException exception) {
            exception.printStackTrace(System.err);
            System.exit(1);
        }

    }
}
