package byog.WorldGeneration;

import java.util.List;

enum Direction {
    MINUS_X, MINUS_Y, PLUS_X, PLUS_Y, NONE;

    public static List<Direction> all() {
        return List.of(MINUS_X, MINUS_Y);
    }

    public Direction opposite() {
        switch (this) {
            case PLUS_X:
                return MINUS_X;
            case MINUS_X:
                return PLUS_X;
            case PLUS_Y:
                return MINUS_Y;
            case MINUS_Y:
                return PLUS_Y;
            case NONE:
                return NONE;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
