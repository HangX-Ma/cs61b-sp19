package creatures;

import huglife.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestClorus {
    @Test
    public void testChoose() {
        // Energy >= 1; replicate towards an empty space.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(topEmpty);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);

        // No empty adjacent spaces; stay.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Plip());

        actual = c.chooseAction(surrounded);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // Attack
        c = new Clorus(1.2);
        surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Plip(0.9));

        actual = c.chooseAction(surrounded);
        expected = new Action(Action.ActionType.ATTACK, Direction.RIGHT);

        assertEquals(expected, actual);
    }


}
