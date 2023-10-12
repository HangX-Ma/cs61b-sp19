import java.util.LinkedList;
import java.util.Queue;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    private final Queue<Character> rollCharsQueue;

    // generate constant time hash code
    private final int firstCharUniqueCharsBase;
    private int hashSum = 0;


    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);

        rollCharsQueue = new LinkedList<>();
        for (char c : s.toCharArray()) {
            rollCharsQueue.add(c);
            hashSum = (hashSum * UNIQUECHARS) + c;
        }
        firstCharUniqueCharsBase = (int) Math.pow(UNIQUECHARS, length - 1);
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        hashSum = (hashSum - rollCharsQueue.element() * firstCharUniqueCharsBase) * UNIQUECHARS + c;
        rollCharsQueue.add(c);
        rollCharsQueue.poll();
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (char c : rollCharsQueue) {
            strb.append(c);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return rollCharsQueue.size();
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        if (o.hashCode() != this.hashCode()) {
            return false;
        }

        RollingString other = (RollingString) o;
        return other.toString().equals(this.toString());
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        return hashSum % PRIMEBASE;
    }
}
