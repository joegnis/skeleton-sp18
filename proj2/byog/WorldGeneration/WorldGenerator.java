package byog.WorldGeneration;

import byog.Core.RandomUtils;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class WorldGenerator {
    public WorldGenerator(int worldWidth, int worldHeight, int randomSeed) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.tiles = new TETile[worldWidth][worldHeight];
        for (int i = 0; i < worldWidth; i++)
            for (int j = 0; j < worldHeight; j++)
                tiles[i][j] = Tileset.NOTHING;

        Random random = new Random(randomSeed);
        this.random = random;
        this.roomGenerator = new RoomGenerator(random);
    }

    public TETile[][] make() {
        WalledRect firstRoom = this.makeFirstRoom();
        this.addWalledRect(firstRoom);
        return this.tiles;
    }

    private final TETile[][] tiles;
    private final int worldWidth;
    private final int worldHeight;
    private final Random random;
    private final RoomGenerator roomGenerator;
    private WalledRect curRect;

    private WalledRect makeFirstRoom() {
        int x = RandomUtils.uniform(this.random, this.worldWidth - this.roomGenerator.getMaxWidth());
        int y = RandomUtils.uniform(this.random, this.worldHeight - this.roomGenerator.getMaxHeight());
        return roomGenerator.make(x, y);
    }

    private void addWalledRect(WalledRect rect) {
        if (rect.x() + rect.width() > this.worldWidth) {
            throw new IllegalArgumentException(String.format("Not enough space to add this rect: x + width = %d + %d > tiles' width = %d", rect.x(), rect.width(), this.worldWidth));
        }
        if (rect.y() + rect.height() > this.worldHeight) {
            throw new IllegalArgumentException(String.format("Not enough space to put this rect: y + height = %d + %d > tiles' height = %d", rect.y(), rect.height(), this.worldHeight));
        }

        int startX = rect.x();
        int startY = rect.y();
        int width = rect.width();
        int height = rect.height();
        // Walls
        for (int x = startX; x < startX + width; x++) {
            this.tiles[x][startY] = rect.tileWall();
            this.tiles[x][startY + height - 1] = rect.tileWall();
        }
        for (int y = startY + 1; y < startY + height - 1; y++) {
            this.tiles[startX][y] = rect.tileWall();
            this.tiles[startX + width - 1][y] = rect.tileWall();
        }

        // Floors
        for (int x = startX + 1; x < startX + width - 1; x++) {
            for (int y = startY + 1; y < startY + height - 1; y++) {
                this.tiles[x][y] = rect.tileFloor();
            }
        }
    }

}
