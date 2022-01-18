package byog.WorldGeneration;

import byog.Core.RandomUtils;

import java.util.Random;

class GrowingRectManager {
    private GrowingRect rootRect;

    public GrowingRectManager() {
    }

    public void grow(WorldGenerator world) {
        Random random = world.getRandom();
        RoomFactory roomFactory = RoomFactory.instance();
        int x = RandomUtils.uniform(random, world.getWorldWidth() - roomFactory.getMaxWidth());
        int y = RandomUtils.uniform(random, world.getWorldHeight() - roomFactory.getMaxHeight());
        this.rootRect = new GrowingRect(
                roomFactory.makeRandom(random, x, y),
                RectShape.ROOM,
                RectGrowingDirection.NONE,
                true,
                100
        );
        world.addRoomRect(rootRect.roomRect());
    }
}
