package byog.WorldGeneration;

import byog.Core.RandomUtils;

import java.util.Random;

class HallwayFactory {
    private static final int DEFAULT_WIDTH_MIN = 3;
    private static final int DEFAULT_WIDTH_MAX = 4;
    private static final int DEFAULT_LENGTH_MIN = 3;
    private static final int DEFAULT_LENGTH_MAX = 20;
    private static final HallwayFactory instance = new HallwayFactory();

    private HallwayFactory() {
    }

    public HallwayFactory getInstance() {
        return instance;
    }

    public RoomRect makeRandom(Random random, int startX, int startY) {
        return new RoomRect(
                startX, startY,
                RandomUtils.uniform(random, DEFAULT_WIDTH_MIN, DEFAULT_WIDTH_MAX),
                RandomUtils.uniform(random, DEFAULT_LENGTH_MIN, DEFAULT_LENGTH_MAX)
        );
    }

    public int getMinWidth() {
        return DEFAULT_WIDTH_MIN;
    }

    public int getMaxWidth() {
        return DEFAULT_WIDTH_MAX;
    }

    public int getMinLength() {
        return DEFAULT_LENGTH_MIN;
    }

    public int getMaxLength() {
        return DEFAULT_LENGTH_MAX;
    }
}
