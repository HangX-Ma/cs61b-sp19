import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

    private final PriorityQueue<Flight> flightPQ;

    private int maxStartTime;
    private int minEndTime;

    public FlightSolver(ArrayList<Flight> flights) {
        // minimum heap of start time
        Comparator<Flight> integerComparator = (a, b) -> (b.passengers() - a.passengers());
        flightPQ = new PriorityQueue<>(flights.size(), integerComparator);
        flightPQ.addAll(flights);
    }

    public int solve() {
        int passengerNum = 0;
        Iterator<Flight> flightIterator = flightPQ.iterator();
        Flight firstFlight = flightIterator.hasNext() ? flightIterator.next() : null;

        if (firstFlight != null) {
            // get initial value
            maxStartTime = firstFlight.startTime();
            minEndTime = firstFlight.endTime();
            passengerNum += firstFlight.passengers();

            while (flightIterator.hasNext()) {
                passengerNum += timeOverlapProcess(flightIterator.next());
            }
        }
        return passengerNum;
    }

    private int timeOverlapProcess(Flight nextFlight) {
        // No crossing time range
        if (nextFlight.startTime() > minEndTime || nextFlight.endTime() < maxStartTime) {
            return 0;
        }
        // deal with time range limitation
        if (nextFlight.startTime() >= maxStartTime) {
            maxStartTime = nextFlight.startTime();
        }
        if (nextFlight.endTime() <= minEndTime) {
            minEndTime = nextFlight.endTime();
        }
        return nextFlight.passengers();
    }
}
