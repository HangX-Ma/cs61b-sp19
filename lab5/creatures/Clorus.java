package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    private final int r;
    private final int g;
    private final int b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public void move() {
        energy = Math.max(energy - 0.03, 0.0);
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy = Math.max(energy - 0.01, 0.0);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for (Map.Entry<Direction, Occupant> neighbor : neighbors.entrySet()) {
            if (neighbor.getValue().name().equals("empty")) {
                emptyNeighbors.add(neighbor.getKey());
            }
            if (neighbor.getValue().name().equals("plip")) {
                plipNeighbors.add(neighbor.getKey());
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (!plipNeighbors.isEmpty()) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        }

        // Rule 3
        if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }

    @Override
    public Color color() {
        return new Color(r, g, b);
    }
}
