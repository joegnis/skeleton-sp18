package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private final TETile[][] tiles;
    private final int tilesWidth;
    private final int tilesHeight;
    private final TERenderer renderer;

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(Position other) {
            x = other.x;
            y = other.y;
        }

        public static Position of(int x, int y) {
            return new Position(x, y);
        }
    }

    public HexWorld(int width, int height) {
        tilesWidth = width;
        tilesHeight = height;
        tiles = new TETile[width][height];
        renderer = new TERenderer();
        renderer.initialize(tilesWidth, tilesHeight);

        for (int x = 0; x < tilesWidth; x += 1) {
            for (int y = 0; y < tilesHeight; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void draw() {
        renderer.renderFrame(tiles);
    }

    /**
     * Adds a hexagon to the tiles
     *
     * @param bottomLeft the position of the bottom-left corner of the smallest rectangle that contains the hexagon
     * @param size       the number of tiles of its first row
     */
    public void addHexagon(final Position bottomLeft, final int size, final TETile tileToFill) {
        addHexagonHalf(bottomLeft, size, tileToFill, true);
        addHexagonHalf(bottomLeft, size, tileToFill, false);
    }

    private void addVerticalRowOfHexagonsFromBottom(final Position startPosition, int sizeHexagon, int numHexagons,
                                                   final TETile tileToFill) {
        Position pos = new Position(startPosition);
        for (int i = 0; i < numHexagons; i++) {
            addHexagon(pos, sizeHexagon, tileToFill);
            pos.y += 2 * sizeHexagon;
        }
    }

    private void addHexagonHalf(final Position bottomLeft, final int size,
                                final TETile tileToFill, boolean bottomHalf) {
        // size-1-0 -> size-1 + size
        // size-1-1 -> size-2 + size+2*1
        // size-1-2 -> size-3 + size+2*2
        // ...
        // size-1-(size-1) -> size-1-(size-1) + size+2*(size-1)
        // size-1-(size-1) -> size-1-(size-1) + size+2*(size-1)
        // ...
        // size-1-2 -> size-3 + size+2*2
        // size-1-1 -> size-2 + size+2*1
        // size-1-0 -> size-1 + size
        for (int relY = 0; relY < size; relY++) {
            int y;
            if (bottomHalf) {
                y = bottomLeft.y + relY;
            } else {
                // x coordinates are the same, we just need to flip ys
                y = bottomLeft.y + 2 * size - 1 - relY;
            }
            if (y > tilesWidth) {
                break;
            }
            for (int relX = size - 1 - relY; relX < (size - 1 - relY) + size + 2 * relY; relX++) {
                int x = bottomLeft.x + relX;
                if (x > tilesHeight) {
                    break;  // continues to the next row
                }
                tiles[x][y] = tileToFill;
            }
        }
    }

    public static void main(String[] args) {
        HexWorld world = new HexWorld(50, 50);
        world.addHexagon(Position.of(30, 30), 5, Tileset.WATER);
        world.addVerticalRowOfHexagonsFromBottom(Position.of(0, 6), 3, 3, Tileset.GRASS);
        world.addVerticalRowOfHexagonsFromBottom(Position.of(5, 3), 3, 4, Tileset.FLOWER);
        world.addVerticalRowOfHexagonsFromBottom(Position.of(10, 0), 3, 5, Tileset.MOUNTAIN);
        world.addVerticalRowOfHexagonsFromBottom(Position.of(15, 3), 3, 4, Tileset.TREE);
        world.addVerticalRowOfHexagonsFromBottom(Position.of(20, 6), 3, 3, Tileset.SAND);
        world.draw();
    }
}
