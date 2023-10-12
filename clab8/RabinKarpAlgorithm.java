public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (pattern.length() > input.length()) {
            return -1;
        }

        RollingString patternString = new RollingString(pattern, pattern.length());

        int patternHash = patternString.hashCode();
        int patternLength = patternString.length();

        for (int i = 0; i < input.length() - patternLength + 1; i++) {
            RollingString substr = new RollingString(input.substring(i, i + patternLength), patternLength);
            if (substr.hashCode() == patternHash) {
                if (substr.equals(patternString)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
