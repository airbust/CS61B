package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    static final int WIDTH = 72;
    static final int HEIGHT = 30;

    static final long SEED = 123456;
    static Random RANDOM = new Random(SEED);

    static final Position ORIGIN = new Position(0, 0);
    static final Position DIAGNAL = new Position(WIDTH, HEIGHT);
    static final Room BACKGROUND = new Room(ORIGIN, DIAGNAL);

    public static void randomGenerator(TETile[][] world, int num) {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        ArrayList<Room> rooms = new ArrayList<>();
        Room first = new Room();
        rooms.add(first);
        first.draw(world);

        while (num > 0) {
            int index = RANDOM.nextInt(rooms.size());
            Room curRoom = rooms.get(index);
            Room newRoom = curRoom.generate();
            if (newRoom.isValid()) {
                num -= 1;
                rooms.add(newRoom);
                curRoom.draw(world);
            }
        }

        Wall.generateWall(world);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        randomGenerator(world, 5);

        ter.renderFrame(world);
    }
}
