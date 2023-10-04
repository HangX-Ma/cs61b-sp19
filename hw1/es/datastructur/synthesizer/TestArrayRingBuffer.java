package es.datastructur.synthesizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void emptyDequeue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);

        exception.expect(RuntimeException.class);
        arb.dequeue();
    }

    @Test
    public void emptyPeek() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);

        exception.expect(RuntimeException.class);
        arb.peek();
    }

    @Test
    public void fullEnqueue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(0);

        exception.expect(RuntimeException.class);
        arb.enqueue(1);
    }

    @Test
    public void capabilityTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);

        assertTrue(arb.isEmpty());
        assertEquals(10, arb.capacity());
        assertEquals(0, arb.fillCount());
        arb.enqueue(1);
        assertEquals(10, arb.capacity());
        assertEquals(1, arb.fillCount());
        arb.enqueue(2);
        assertEquals(2, arb.fillCount());
    }

    @Test
    public void peekTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(arb.peek(), 1);
        assertEquals(arb.dequeue(), 1);
    }

    @Test
    public void loopTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(5);
        arb.enqueue(6);
        assertEquals(arb.peek(), 2);
    }
}
