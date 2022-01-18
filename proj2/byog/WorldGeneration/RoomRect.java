package byog.WorldGeneration;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

class RoomRect {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final TETile tileWall;
    private final TETile tileFloor;

    public RoomRect(int x, int y, int width, int height, TETile tileWall, TETile tileFloor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tileWall = tileWall;
        this.tileFloor = tileFloor;
    }

    public RoomRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tileWall = Tileset.WALL;
        this.tileFloor = Tileset.FLOOR;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TETile getTileWall() {
        return tileWall;
    }

    public TETile getTileFloor() {
        return tileFloor;
    }
}
