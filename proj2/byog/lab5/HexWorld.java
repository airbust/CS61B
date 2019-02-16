package byog.lab5;
import javafx.geometry.Pos;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /* @Source: Josh Hug */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static class Position {
        public int x;
        public int y;

        public Position(int xCoord, int yCoord) {
            x = xCoord;
            y = yCoord;
        }
    }

    /* The row number starts with i = 0, which is the bottom. */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;
            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);
            int rowWidth = hexRowWidth(s, yi);
            addRow(world, rowStartP, rowWidth, t);
        }
    }

    public static void drawRandomVerticalHexes(TETile[][] world, Position p, int s, int N) {
        Position nextp = new Position(p.x, p.y);
        for (int i = 0; i < N; i += 1) {
            addHexagon(world, nextp, s, randomTile());
            nextp.y += 2 * s;
        }
    }

    public static void drawTesselationofHexagons(TETile[][] world, Position p, int s) {
        Position nextp = new Position(p.x, p.y);
        int xOffset = xOffset(s);
        for (int i = 0; i < 5; i += 1) {
            drawRandomVerticalHexes(world, nextp, s, hexagonNum(i));
            nextp.x += xOffset;
            nextp.y += yOffset(s, i + 1); // i + 1, not i
        }
    }

    private static int hexagonNum(int i) {
        int N = i + 3;
        if (i > 2) {
            N = 10 - N;
        }
        return N;
    }

    private static int xOffset(int s) {
        return 2 * s + hexRowOffset(s, 0) - 1;
    }

    private static int yOffset(int s, int i) {
        int yOff = s;
        if (i > 2) {
            return yOff;
        }
        return -yOff;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            default: return Tileset.TREE;
        }
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Position p = new Position(3, 10);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
//        addHexagon(world, p, 2, Tileset.MOUNTAIN);
//        drawRandomVerticalHexes(world, p, 2, 3);
        drawTesselationofHexagons(world, p, 4);
        ter.renderFrame(world);
    }
}
