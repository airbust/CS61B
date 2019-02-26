package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("Clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return new Color(r, g, b);
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public Clorus replicate() {
        Clorus offspring = new Clorus(energy / 2);
        energy /= 2;
        return offspring;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");

        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else {
            if (plips.size() > 0) {
                Direction dir = HugLifeUtils.randomEntry(plips);
                return new Action(Action.ActionType.ATTACK, dir);
            } else {
                if (energy >= 1) {
                    Direction dir = HugLifeUtils.randomEntry(empties);
                    return new Action(Action.ActionType.REPLICATE, dir);
                } else {
                    Direction dir = HugLifeUtils.randomEntry(empties);
                    return new Action(Action.ActionType.MOVE, dir);
                }
            }
        }
    }
}
