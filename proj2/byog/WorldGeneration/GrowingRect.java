package byog.WorldGeneration;

import byog.Core.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;

class GrowingRect {
    private final RoomRect rect;
    private final RectShape shape;
    private final Map<Direction, GrowingRect> children;
    private boolean isTrunk;

    public GrowingRect(RoomRect rect, RectShape shape) {
        this.rect = rect;
        this.shape = shape;
        this.children = new HashMap<>();
        this.isTrunk = false;
    }

    public GrowingRect growAsTrunk(WorldGenerator world) {
        Random random = world.getRandom();
        Map<Direction, Double> distancesToEdge = Map.of(
                Direction.MINUS_X, rect.getX() + rect.getWidth() / 2.0d,
                Direction.PLUS_X, world.getWorldWidth() - (rect.getX() + rect.getWidth() / 2.0d),
                Direction.MINUS_Y, rect.getY() + rect.getHeight() / 2.0d,
                Direction.PLUS_Y, world.getWorldHeight() - (rect.getY() + rect.getHeight() / 2.0d)
        );
        Set<Map.Entry<Direction, Double>> directionsToChoose = distancesToEdge.entrySet().stream()
                .filter(entry -> !children.containsKey(entry.getKey()))
                .collect(Collectors.toSet());
        if (directionsToChoose.isEmpty()) {
            throw new RuntimeException("No direction to grow");
        }
        Map.Entry<Direction, Double> maxEntry = directionsToChoose.stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue)).get();
        Direction directionToGrow = maxEntry.getKey();
        if (directionsToChoose.size() > 1 && RandomUtils.uniform(random) >= 0.9) {
            Direction d = directionToGrow;
            List<Map.Entry<Direction, Double>> rest = directionsToChoose.stream()
                    .filter(entry -> entry.getKey() != d)
                    .collect(Collectors.toList());
            directionToGrow = rest.get(random.nextInt(rest.size())).getKey();
        }

        GrowingRect child = growAtDirection(directionToGrow, world);
        isTrunk = true;
        child.isTrunk = true;
        children.put(directionToGrow, child);
        child.children.put(directionToGrow.opposite(), this);
        return child;
    }

    public void putChild(Direction direction, GrowingRect child) {
        children.put(direction, child);
    }

    public RoomRect getRect() {
        return rect;
    }

    public boolean getIsTrunk() {
        return isTrunk;
    }

    public RectShape getShape() {
        return shape;
    }

    private GrowingRect growAtDirection(Direction direction, WorldGenerator world) {
        Random random = world.getRandom();
        // TODO: only Room shape for now
        RoomFactory roomFactory = RoomFactory.instance();
        RoomRect childRect = roomFactory.makeRandomOnRect(random, rect, direction);
        return new GrowingRect(childRect, RectShape.ROOM);
    }
}
