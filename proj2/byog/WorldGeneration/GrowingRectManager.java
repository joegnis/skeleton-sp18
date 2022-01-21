package byog.WorldGeneration;

import byog.Core.RandomUtils;

import java.util.Random;

class GrowingRectManager {
    private GrowingRect firstGrowingRect;

    public void grow(WorldGenerator world) {
        makeChunk(world);
    }

    RoomRect getFirstGrowingRect() {
        return firstGrowingRect.getRect();
    }

    private void makeChunk(WorldGenerator world) {
        Random random = world.getRandom();
        RoomFactory roomFactory = RoomFactory.instance();
        int x = RandomUtils.uniform(random, world.getWorldWidth() - roomFactory.getMaxWidth());
        int y = RandomUtils.uniform(random, world.getWorldHeight() - roomFactory.getMaxHeight());
        RoomRect firstRect = roomFactory.makeRandom(random, x, y);
        firstGrowingRect = new GrowingRect(firstRect, RectShape.ROOM);

        GrowingRect current = firstGrowingRect;
        for (int i = 0; i < 5; i++) {
            world.addRoomRect(current.getRect(), firstRect.getX(), firstRect.getY());
            current = current.growAsTrunk(world);
        }
    }
}
