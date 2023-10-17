package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome solverState = SolverOutcome.UNSOLVABLE;
    private final LinkedList<Vertex> solution;
    private double solutionWeight = 0;
    private int pqDequeueNum = 0;
    private final double explorationTime;

    /** Constructor which finds the solution, computing everything
     *  necessary for all other methods to return their results in
     *  constant time. Note that timeout passed in is in seconds. */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        ArrayHeapMinPQ<Vertex> doubleMapPQ = new ArrayHeapMinPQ<>();
        Map<Vertex, Double> distToStart = new HashMap<>();
        Map<Vertex, Double> distToEnd = new HashMap<>();
        Map<Vertex, Vertex> edgeTo = new HashMap<>();
        solution = new LinkedList<>();

        // deal with the start vertex
        distToStart.put(start, 0.0);
        doubleMapPQ.add(start, distToStart.get(start));

        Stopwatch sw = new Stopwatch();
        Vertex v, from, to;
        double weight, newDist, heuristic;
        while(doubleMapPQ.size() != 0) {
            if (sw.elapsedTime() >= timeout) {
                solverState = SolverOutcome.TIMEOUT;
                break;
            }
            v = doubleMapPQ.getSmallest();

            if (v.equals(end)) {
                solverState = SolverOutcome.SOLVED;
                solutionWeight = distToStart.get(v);
                while (!v.equals(start)) {
                    solution.addFirst(v);
                    v = edgeTo.get(v);
                }
                solution.addFirst(v); // Add the start vertex
                break;
            }

            v = doubleMapPQ.removeSmallest();
            pqDequeueNum += 1;
            for (WeightedEdge<Vertex> neighbor : input.neighbors(v)) {
                if (sw.elapsedTime() >= timeout) {
                    solverState = SolverOutcome.TIMEOUT;
                    break;
                }
                from = neighbor.from();
                to = neighbor.to();
                weight = neighbor.weight();
                newDist = distToStart.get(from) + weight;

                /* check if we have explored the 'to' node */
                if (!distToStart.containsKey(to)) {
                    distToStart.put(to, Double.POSITIVE_INFINITY);
                }
                if (!distToEnd.containsKey(to)) {
                    distToEnd.put(to, input.estimatedDistanceToGoal(to, end));
                }

                /* relax the edges */
                if (newDist < distToStart.get(to)) {
                    distToStart.put(to, newDist);

                    // Update the edge used by the dest vertex.
                    edgeTo.put(to, from);

                    heuristic = distToStart.get(to) + distToEnd.get(to);
                    if (!doubleMapPQ.contains(to)) {
                        doubleMapPQ.add(to, heuristic);
                    } else {
                        doubleMapPQ.changePriority(to, heuristic);
                    }
                }
            }
        }
        explorationTime = sw.elapsedTime();
    }

    /** Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
     * - Should be SOLVED if the AStarSolver was able to complete all work in the time given.
     * - UNSOLVABLE if the priority queue became empty.
     * - TIMEOUT if the solver ran out of time.
     *  You should check to see if you have run out of time every time you dequeue. */
    public SolverOutcome outcome() {
        return solverState;
    }

    /** A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE. */
    public List<Vertex> solution() {
        if (outcome() != SolverOutcome.SOLVED) {
            return new ArrayList<>();
        }
        return solution;
    }

    /** The total weight of the given solution, taking into account
     *  edge weights. Should be 0 if result was TIMEOUT or UNSOLVABLE. */
    public double solutionWeight() {
        if (outcome() != SolverOutcome.SOLVED) {
            return 0;
        }
        return solutionWeight;
    }

    /** The total number of priority queue dequeue operations. */
    public int numStatesExplored() {
        return pqDequeueNum;
    }

    /** The total time spent in seconds by the constructor. */
    public double explorationTime() {
        return explorationTime;
    }
}