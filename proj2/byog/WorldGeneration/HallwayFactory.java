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

    public RoomRect makeRandom(Random random, int startX, int startY, Direction direction) {
        int width = RandomUtils.uniform(random, DEFAULT_WIDTH_MIN, DEFAULT_WIDTH_MAX);
        int length = RandomUtils.uniform(random, DEFAULT_LENGTH_MIN, DEFAULT_LENGTH_MAX);
        int realWidth = width;
        int realHeight = length;
        int realX = startX;
        int realY = startY;
        switch (direction) {
            case PLUS_X:
                realWidth = length;
                realHeight = width;
                break;
            case MINUS_X:
                realX -= length;
                realWidth = length;
                realHeight = width;
                break;
            case PLUS_Y:
                break;
            case MINUS_Y:
                realY -= length;
                break;
            case NONE:
                throw new IllegalArgumentException("Direction is None.");
        }
        return new RoomRect(realX, realY, realWidth, realHeight);
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
