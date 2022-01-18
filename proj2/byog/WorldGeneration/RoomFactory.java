package byog.WorldGeneration;

import byog.Core.RandomUtils;

import java.util.Random;

class RoomFactory {
    private static final RoomFactory instance = new RoomFactory();

    private RoomFactory() {
    }

    public int getMinWidth() {
        return DEFAULT_WIDTH_MIN;
    }

    public int getMaxWidth() {
        return DEFAULT_WIDTH_MAX;
    }

    public int getMinHeight() {
        return DEFAULT_HEIGHT_MIN;
    }

    public int getMaxHeight() {
        return DEFAULT_HEIGHT_MAX;
    }

    private static final int DEFAULT_WIDTH_MIN = 3;
    private static final int DEFAULT_WIDTH_MAX = 12;
    private static final int DEFAULT_HEIGHT_MIN = 3;
    private static final int DEFAULT_HEIGHT_MAX = 12;

    public static RoomFactory instance() {
        return instance;
    }

    public RoomRect makeRandom(Random random, int startX, int startY) {
        return new RoomRect(
                startX, startY,
                RandomUtils.uniform(random, DEFAULT_WIDTH_MIN, DEFAULT_WIDTH_MAX),
                RandomUtils.uniform(random, DEFAULT_HEIGHT_MIN, DEFAULT_HEIGHT_MAX)
        );
    }
}
