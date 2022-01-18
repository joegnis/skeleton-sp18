package byog.WorldGeneration;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class WorldGenerator {
    private static final int DEFAULT_RANDOM_SEED = 873364;
    private final Random random;
    private final TETile[][] tiles;
    private final int worldWidth;
    private final int worldHeight;
    private final GrowingRectManager rectManager;

    public WorldGenerator(int worldWidth, int worldHeight, int randomSeed) {
        this.random = new Random(randomSeed);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.tiles = new TETile[worldWidth][worldHeight];
        for (int i = 0; i < worldWidth; i++)
            for (int j = 0; j < worldHeight; j++)
                tiles[i][j] = Tileset.NOTHING;

        this.rectManager = new GrowingRectManager();
    }

    public WorldGenerator(int worldWidth, int worldHeight) {
        this(worldWidth, worldHeight, DEFAULT_RANDOM_SEED);
    }

    public TETile[][] makeRandom() {
        // Adds the first room
        rectManager.grow(this);
        return this.tiles;
    }

    public TETile[][] getTiles() {
        return tiles;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public Random getRandom() {
        return random;
    }

    void addRoomRect(RoomRect rect) {
        if (rect.getX() + rect.getWidth() > this.worldWidth) {
            throw new IllegalArgumentException(String.format("Not enough space to add this rect: x + width = %d + %d > tiles' width = %d", rect.getX(), rect.getWidth(), this.worldWidth));
        }
        if (rect.getY() + rect.getHeight() > this.worldHeight) {
            throw new IllegalArgumentException(String.format("Not enough space to put this rect: y + height = %d + %d > tiles' height = %d", rect.getY(), rect.getHeight(), this.worldHeight));
        }

        int startX = rect.getX();
        int startY = rect.getY();
        int width = rect.getWidth();
        int height = rect.getHeight();
        // Walls
        for (int x = startX; x < startX + width; x++) {
            this.tiles[x][startY] = rect.getTileWall();
            this.tiles[x][startY + height - 1] = rect.getTileWall();
        }
        for (int y = startY + 1; y < startY + height - 1; y++) {
            this.tiles[startX][y] = rect.getTileWall();
            this.tiles[startX + width - 1][y] = rect.getTileWall();
        }

        // Floors
        for (int x = startX + 1; x < startX + width - 1; x++) {
            for (int y = startY + 1; y < startY + height - 1; y++) {
                this.tiles[x][y] = rect.getTileFloor();
            }
        }
    }
}
