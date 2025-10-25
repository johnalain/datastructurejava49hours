import java.util.*;

public class LongestSubstring {

    public static int lengthOfLongestSubstring(String s) {
        int start = 0, maxLength = 0;
        Map<Character, Integer> seen = new HashMap<>();

        for (int end = 0; end < s.length(); end++) {
            char current = s.charAt(end);

            if (seen.containsKey(current) && seen.get(current) >= start) {
                start = seen.get(current) + 1;
            }

            seen.put(current, end);
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String s = sc.nextLine();

        int result = lengthOfLongestSubstring(s);
        System.out.println("Length of the largest substring without repeating characters: " + result);
        
        sc.close();
    }
}
