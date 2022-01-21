package byog.WorldGeneration;

import byog.Core.RandomUtils;

import java.util.Random;

class RoomFactory {
    private static final RoomFactory instance = new RoomFactory();
    private static final int DEFAULT_WIDTH_MIN = 3;
    private static final int DEFAULT_WIDTH_MAX = 12;
    private static final int DEFAULT_HEIGHT_MIN = 3;
    private static final int DEFAULT_HEIGHT_MAX = 12;

    private RoomFactory() {
    }

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

    public RoomRect makeRandomOnRect(Random random, RoomRect parent, Direction direction) {
        int width = RandomUtils.uniform(random, DEFAULT_WIDTH_MIN, DEFAULT_WIDTH_MAX);
        int height = RandomUtils.uniform(random, DEFAULT_HEIGHT_MIN, DEFAULT_HEIGHT_MAX);
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();
        int x = parent.getX();
        int y = parent.getY();

        switch (direction) {
            case PLUS_X:
                x += parentWidth + width;
                x -= 2;
            case MINUS_X:
                x -= width;
                x += 1;
                height = Math.min(height, parentHeight);
                if (height < parentHeight && RandomUtils.uniform(random) < 0.5) {
                    y += parentHeight - height;
                }
                break;
            case PLUS_Y:
                y += parentHeight + width;
                y -= 2;
            case MINUS_Y:
                y -= width;
                y += 1;
                width = Math.min(width, parentWidth);
                if (width < parentWidth && RandomUtils.uniform(random) < 0.5) {
                    x += parentWidth - width;
                }
                break;
            case NONE:
                throw new IllegalArgumentException("Direction is NONE");
        }
        return new RoomRect(x, y, width, height);
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
}
