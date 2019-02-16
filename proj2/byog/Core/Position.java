package byog.Core;

import static byog.Core.MapGenerator.*;

public class Position {
    public int x;
    public int y;

    public Position(int x0, int y0) {
        x = x0;
        y = y0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Position that = (Position) o;
        return (this.x == that.x) && (this.y == that.y);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public boolean isInside(Room room) {
        return (x > room.p.x) && (x <= room.q.x)
                && (y > room.p.y) && (y <= room.q.y);
    }

    public Position move(Position o) {
        return new Position(this.x + o.x, this.y + o.y);
    }

    public static Position randomPosition() {
        int x = RANDOM.nextInt(WIDTH);
        int y = RANDOM.nextInt(HEIGHT);
        return new Position(x, y);
    }

    public static Position randomPositionInsideRoom(Room r) {
        int choice = RANDOM.nextInt(4);
        int width = r.roomWidth();
        int height = r.roomHeight();
        switch (choice) {
            case 0: return r.p.move(new Position(RANDOM.nextInt(width), 0));
            case 1: return r.p.move(new Position(0, RANDOM.nextInt(height)));
            case 2: return r.q.move(new Position(-RANDOM.nextInt(width), 0));
            case 3: return r.q.move(new Position(0, -RANDOM.nextInt(height)));
            default: return ORIGIN;
        }
    }

    public static void regularization(Position p, Position q) {
        int tmp;
        if (p.x > q.x) {
            tmp = p.x;
            p.x = q.x;
            q.x = tmp;
        }
        if (p.y > q.y) {
            tmp = p.y;
            p.y = q.y;
            q.y = tmp;
        }
    }
}
