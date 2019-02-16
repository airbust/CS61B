package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import static byog.Core.MapGenerator.*;

public class Wall {
    public static void generateWall(TETile[][] world) {
        int count = 0;
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    count = 1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    count = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int j = 0; j < HEIGHT; j += 1) {
            for (int i = 0; i < WIDTH; i += 1) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    count = 1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    count = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int i = WIDTH - 1; i >= 0; i -= 1) {
            for (int j = HEIGHT - 1; j >= 0; j -= 1) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    count = 1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    count = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int j = HEIGHT - 1; j >= 0; j -= 1) {
            for (int i = WIDTH - 1; i >= 0; i -= 1) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    count = 1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    count = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }
}
