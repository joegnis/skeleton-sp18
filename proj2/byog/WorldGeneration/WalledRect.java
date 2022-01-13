package byog.WorldGeneration;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

class WalledRect {
    public WalledRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tileFloor = DEFAULT_TILE_FLOOR;
        this.tileWall = DEFAULT_TILE_WALL;
    }

    public WalledRect(int width, int height) {
        this(-1, -1, width, height);
    }

    public int x() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int y() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int width() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TETile tileWall() {
        return this.tileWall;
    }

    public TETile tileFloor() {
        return this.tileFloor;
    }

    public void setWallTile(TETile tile) {
        this.tileWall = tile;
    }

    public void setFloorTile(TETile tile) {
        this.tileFloor = tile;
    }

    private static final TETile DEFAULT_TILE_FLOOR = Tileset.FLOOR;
    private static final TETile DEFAULT_TILE_WALL = Tileset.WALL;
    private int x;
    private int y;
    private int width;
    private int height;
    private TETile tileWall;
    private TETile tileFloor;
}
