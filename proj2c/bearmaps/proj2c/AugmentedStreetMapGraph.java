package bearmaps.proj2c;

import bearmaps.lab9.MyTrieSet;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.KDTree;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    Map<Point, Long> pointToID;
    KDTree kdTree;
    MyTrieSet trie;
    Map<String, List<Node>> cleanedNameToNodes;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();

        pointToID = new HashMap<>();
        trie = new MyTrieSet();
        cleanedNameToNodes = new HashMap<>();

        List<Node> nodesList;

        for (Node node : nodes) {
            long id = node.id();
            if (node.name() != null) {
                String cleanedName = cleanString(node.name());
                trie.add(cleanedName);

                /* Collect the nodes correspond to the cleaned name (N to 1 relationship) */
                if (!cleanedNameToNodes.containsKey(cleanedName)) {
                    cleanedNameToNodes.put(cleanedName, new LinkedList<>());
                }
                // update the node list
                nodesList = cleanedNameToNodes.get(cleanedName);
                nodesList.add(node);
                cleanedNameToNodes.put(cleanedName, nodesList);
            }

            /* Only search the nodes with neighbors */
            if (!this.neighbors(id).isEmpty()) {
                Point point = new Point(node.lon(), node.lat());

                points.add(point);
                pointToID.put(point, id);
            }
        }

        kdTree = new KDTree(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point closestPoint = kdTree.nearest(lon, lat);
        return pointToID.get(closestPoint);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanedPrefix = cleanString(prefix);
        List<String> matchedNames = trie.keysWithPrefix(cleanedPrefix);
        List<String> locations = new LinkedList<>();

        for (String name : matchedNames) {
            for (Node node : cleanedNameToNodes.get(name)) {
                if (!locations.contains(node.name())) {
                    locations.add(node.name());
                }
            }
        }

        return locations;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> locations = new LinkedList<>();
        String cleanedLocationName = cleanString(locationName);
        for (Node node : cleanedNameToNodes.get(cleanedLocationName)) {
            Map<String, Object> info = new HashMap<>();
            info.put("lat", node.lat());
            info.put("lon", node.lon());
            info.put("name", node.name());
            info.put("id", node.id());
            locations.add(info);
        }

        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
