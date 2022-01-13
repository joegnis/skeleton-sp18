package byog.WorldGeneration;

import byog.Core.RandomUtils;

import java.util.Random;

class RoomGenerator {
    public RoomGenerator(Random random) {
        this.random = random;
    }

    public WalledRect make(int startX, int startY) {
        return new WalledRect(
                startX, startY,
                RandomUtils.uniform(this.random, DEFAULT_WIDTH_MIN, DEFAULT_WIDTH_MAX),
                RandomUtils.uniform(this.random, DEFAULT_HEIGHT_MIN, DEFAULT_HEIGHT_MAX)
        );
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
    private final Random random;
}
