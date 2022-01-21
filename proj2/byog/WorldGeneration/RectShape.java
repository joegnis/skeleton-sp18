package byog.WorldGeneration;

import java.util.List;

enum RectShape {
    ROOM, HALLWAY;

    public static int countAll() {
        return 2;
    }

    public static List<RectShape> all() {
        return List.of(ROOM, HALLWAY);
    }
}
