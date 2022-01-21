package byog.WorldGeneration;

import byog.Core.Utils;
import byog.Core.WeightedQuickUnionPC;
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
    private final WeightedQuickUnionPC occupiedTiles;

    public WorldGenerator(int worldWidth, int worldHeight, int randomSeed) {
        this.random = new Random(randomSeed);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.tiles = new TETile[worldWidth][worldHeight];
        for (int i = 0; i < worldWidth; i++)
            for (int j = 0; j < worldHeight; j++)
                tiles[i][j] = Tileset.NOTHING;

        this.rectManager = new GrowingRectManager();
        this.occupiedTiles = new WeightedQuickUnionPC(worldWidth * worldHeight);
    }

    public WorldGenerator(int worldWidth, int worldHeight) {
        this(worldWidth, worldHeight, DEFAULT_RANDOM_SEED);
    }

    public TETile[][] makeRandom() {
        rectManager.grow(this);
        return this.tiles;
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

    TETile[][] getTiles() {
        return tiles;
    }

    void addRoomRect(RoomRect rect, int occupiedX, int occupiedY) {
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
        int occupiedParentTile = Utils.rowColTo1D(occupiedX, occupiedY, worldWidth);
        // Walls
        for (int x = startX; x < startX + width; x++) {
            int rightWallY = startY + height - 1;
            tiles[x][startY] = rect.getTileWall();
            tiles[x][rightWallY] = rect.getTileWall();
            occupiedTiles.union(occupiedParentTile, Utils.rowColTo1D(x, startY, worldWidth));
            occupiedTiles.union(occupiedParentTile, Utils.rowColTo1D(x, rightWallY, worldWidth));
        }
        for (int y = startY + 1; y < startY + height - 1; y++) {
            int topWallX = startX + width - 1;
            tiles[startX][y] = rect.getTileWall();
            tiles[topWallX][y] = rect.getTileWall();
            occupiedTiles.union(occupiedParentTile, Utils.rowColTo1D(startX, y, worldWidth));
            occupiedTiles.union(occupiedParentTile, Utils.rowColTo1D(topWallX, y, worldWidth));
        }

        // Floors
        for (int x = startX + 1; x < startX + width - 1; x++) {
            for (int y = startY + 1; y < startY + height - 1; y++) {
                tiles[x][y] = rect.getTileFloor();
                occupiedTiles.union(occupiedParentTile, Utils.rowColTo1D(x, y, worldWidth));
            }
        }
    }

    boolean areTilesConnected(int tile1X, int tile1Y, int tile2X, int tile2Y) {
        return occupiedTiles.connected(
                Utils.rowColTo1D(tile1X, tile1Y, worldWidth),
                Utils.rowColTo1D(tile2X, tile2Y, worldWidth)
        );
    }
}
