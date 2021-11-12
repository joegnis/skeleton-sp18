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
     * @param startX the x coordinate of the bottom-left corner of the smallest rectangle that contains the hexagon
     * @param startY the y coordinate of the bottom-left corner of the smallest rectangle that contains the hexagon
     * @param size   the number of tiles of its first row
     */
    public void addHexagon(final int startX, final int startY, final int size, final TETile tileToFill) {
        addHexagonHalf(startX, startY, size, tileToFill, true);
        addHexagonHalf(startX, startY, size, tileToFill, false);
    }

    private void addHexagonHalf(final int startX, final int startY,
                                final int size, final TETile tileToFill, boolean bottomHalf) {
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
                y = startY + relY;
            } else {
                // x coordinates are the same, we just need to flip ys
                y = startY + 2 * size - 1 - relY;
            }
            if (y > tilesWidth) {
                break;
            }
            for (int relX = size - 1 - relY; relX < (size - 1 - relY) + size + 2 * relY; relX++) {
                int x = startX + relX;
                if (x > tilesHeight) {
                    break;  // continues to the next row
                }
                tiles[x][y] = tileToFill;
            }
        }
    }

    public static void main(String[] args) {
        HexWorld world = new HexWorld(50, 50);
        world.addHexagon(25, 25, 5, Tileset.FLOWER);
        world.draw();
    }
}
