package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.WorldGeneration.WorldGenerator;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Game {
    public Game(int seed) {
        this.renderer = new TERenderer();
        renderer.initialize(WIDTH, HEIGHT);
        this.worldGenerator = new WorldGenerator(WIDTH, HEIGHT, seed);
        this.state = new MenuState();
    }

    public Game() {
        this(new Random().nextInt());
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        this.renderMenu();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                state = state.handleInput(StdDraw.nextKeyTyped());
                if (state instanceof ExitState) {
                    break;
                }
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    private static final int WIDTH = 80;  // unit: tile size
    private static final int HEIGHT = 45;  // unit: tile size
    private static final Color COLOR_BACKGROUND = StdDraw.BLACK;

    private final TERenderer renderer;
    private final WorldGenerator worldGenerator;

    private IGameState state;

    private interface IGameState {
        IGameState handleInput(char input);
        void enter();
        void exit();
    }

    private abstract class BaseState implements IGameState {

        @Override
        public IGameState handleInput(char input) {
            if (input == 'q') {
                return setState(new ExitState());
            }
            return this;
        }

        @Override
        public void enter() {
        }

        @Override
        public void exit() {
        }


        protected IGameState setState(IGameState next) {
            this.exit();
            next.enter();
            return next;
        }
    }

    private class MenuState extends BaseState {
        @Override
        public IGameState handleInput(char input) {
            if (input == 'n') {
                return setState(new PlayState());
            }
            return super.handleInput(input);
        }
    }

    private class PlayState extends BaseState {
        @Override
        public void enter() {
            renderer.renderFrame(worldGenerator.make());
        }
    }

    private class ExitState extends BaseState {

        @Override
        public void enter() {
            renderExitScreen();
        }
    }

    private void renderMenu() {
        StdDraw.clear(COLOR_BACKGROUND);
        StdDraw.setPenColor(StdDraw.WHITE);

        int tileSize = this.renderer.getTileSize();
        Font titleFont = new Font("Monaco", Font.BOLD, tileSize * 3);
        double titleCenterX = WIDTH / 2.0d;
        double titleCenterY = HEIGHT / 4.0d * 3;
        StdDraw.setFont(titleFont);
        StdDraw.text(titleCenterX, titleCenterY, "THE GAME");

        Font menuItemFont = new Font("Monaco", Font.PLAIN, tileSize);
        List<String> menuItems = List.of("New Game (N)", "Quit (Q)");
        double menuItemX = WIDTH / 2.0d;
        double menuItemY = HEIGHT / 2.0d;
        double menuItemGap = 1 / 2.0d;
        StdDraw.setFont(menuItemFont);
        for (String menuItem : menuItems) {
            StdDraw.text(menuItemX, menuItemY, menuItem);
            menuItemY -= 1 + menuItemGap;
        }

        StdDraw.show();
    }

    private void renderExitScreen() {
        StdDraw.clear(COLOR_BACKGROUND);
        StdDraw.setPenColor(StdDraw.WHITE);

        int tileSize = this.renderer.getTileSize();
        Font titleFont = new Font("Monaco", Font.BOLD, tileSize * 2);
        double titleCenterX = WIDTH / 2.0d;
        double titleCenterY = HEIGHT / 2.0d;
        StdDraw.setFont(titleFont);
        StdDraw.text(titleCenterX, titleCenterY, "You can close the window now");

        StdDraw.show();
    }
}
