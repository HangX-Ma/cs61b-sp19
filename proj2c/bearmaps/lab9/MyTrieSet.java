package bearmaps.lab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B {

    private TrieNode root;

    public MyTrieSet() {
        root = new TrieNode('\0', false);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean contains(String key) {
        if (key == null || root == null || key.isEmpty()) {
            return false;
        }

        TrieNode currNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            currNode = currNode.child.get(c);
            if (currNode == null) {
                return false;
            }
        }
        return currNode.isKey;
    }

    @Override
    public void add(String key) {
        if (key == null || key.isEmpty() || root == null) {
            return;
        }
        TrieNode currNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!currNode.child.containsKey(c)) {
                currNode.child.put(c, new TrieNode(c, false));
            }
            currNode = currNode.child.get(c);
        }
        currNode.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty() || root == null) {
            throw new IllegalArgumentException();
        }
        List<String> result = new ArrayList<>();
        TrieNode startNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            startNode = startNode.child.get(c);
            // return earlier if the trie has no such prefix
            if (startNode == null) {
                return result;
            }
        }
        // Add prefix itself if its final node is the key
        if (startNode.isKey) {
            result.add(prefix);
        }
        for (TrieNode node : startNode.child.values()) {
            if (node != null) {
                keysWithPrefix(result, prefix, node);
            }
        }
        return result;
    }

    private void keysWithPrefix(List<String> result, String prefix, TrieNode startNode) {
        if (startNode.isKey) {
            result.add(prefix + startNode.c);
        }
        for (TrieNode node : startNode.child.values()) {
            if (node != null) {
                keysWithPrefix(result, prefix + startNode.c, node);
            }
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        if (key == null || key.isEmpty() || root == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder resultString = new StringBuilder();
        TrieNode currNode = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            currNode = currNode.child.get(c);
            if (currNode == null) {
                break;
            }
            resultString.append(c);
        }
        return resultString.toString();
    }

    static private class TrieNode {
        private final char c;
        private boolean isKey;
        private final Map<Character, TrieNode> child;

        public TrieNode(char c, boolean isKey) {
            child = new HashMap<>();
            this.isKey = isKey;
            this.c = c;
        }
    }
}
