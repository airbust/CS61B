package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import static byog.Core.MapGenerator.*;

public class Room {
    public Position p;
    public Position q;

    public Room(Position p, Position q) {
        Position.regularization(p, q);
        this.p = p;
        this.q = q;
    }

    public Room() {
        Position p = Position.randomPosition();
        Position q = Position.randomPosition();
        while (p.equals(q)) {
            q = Position.randomPosition();
        }
        Position.regularization(p, q);
        this.p = p;
        this.q = q;
    }

    public Room generate() {
        Position p = Position.randomPositionInsideRoom(this);
        if (this.isHallway()) {
            Position q = Position.randomPosition();
            while (q.isInside(this)) {
                q = Position.randomPosition();
            }
            return new Room(p, q);
        }
        return generateHallway(p);
    }

    public Room generateHallway(Position p) {
        Position q = p;
        while(q.isInside(this)) {
            int x = RANDOM.nextInt(2);
            int diff = 2 * RANDOM.nextInt(2) - 1;
            if (x == 0) {
                int xEnd = RANDOM.nextInt(WIDTH);
                q = new Position(xEnd, p.y + diff);
            } else {
                int yEnd = RANDOM.nextInt(HEIGHT);
                q = new Position(p.x + diff, yEnd);
            }
        }
        return new Room(p, q);
    }

    public boolean isValid() {
        if (!p.isInside(BACKGROUND) || !q.isInside(BACKGROUND) || p.equals(q)) {
            return false;
        }
        return true;
    }

    public void draw(TETile[][] world) {
        for (int i = p.x; i < q.x; i += 1) {
            for (int j = p.y; j < q.y; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    public boolean isHallway() {
        return (Math.abs(p.x - q.x) == 1) || (Math.abs(p.y - q.y) == 1);
    }

    public int roomWidth() {
        return q.x - p.x;
    }

    public int roomHeight() {
        return q.y - p.y;
    }
}
