package byog.WorldGeneration;

import java.util.HashMap;
import java.util.Map;

class GrowingRect {
    private final RoomRect roomRect;
    private final RectGrowingDirection direction;
    private final boolean isTrunk;
    private final double chanceGrowing;
    private final RectShape shape;
    private final Map<RectGrowingDirection, GrowingRect> children;

    public GrowingRect(
            RoomRect roomRect, RectShape shape, RectGrowingDirection direction,
            boolean isTrunk, double chanceGrowing) {
        this.roomRect = roomRect;
        this.shape = shape;
        this.isTrunk = isTrunk;
        this.direction = direction;
        this.chanceGrowing = chanceGrowing;
        this.children = new HashMap<>();
    }

    public void putChild(RectGrowingDirection direction, GrowingRect child) {
        children.put(direction, child);
    }

    public RoomRect roomRect() {
        return roomRect;
    }

    public RectGrowingDirection direction() {
        return direction;
    }

    public boolean isTrunk() {
        return isTrunk;
    }

    public double chanceGrowing() {
        return chanceGrowing;
    }

    public RectShape shape() {
        return shape;
    }
}
