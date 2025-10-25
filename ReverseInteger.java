import java.util.Scanner;

public class ReverseInteger {

    public static int reverse(int num) {
        int reversed = 0;

        while (num != 0) {
            int digit = num % 10;
            // Check for overflow before multiplying by 10
            if (reversed > Integer.MAX_VALUE / 10 || reversed < Integer.MIN_VALUE / 10) {
                System.out.println("Overflow! Number too large to reverse.");
                return 0;
            }
            reversed = reversed * 10 + digit;
            num /= 10;
        }

        return reversed;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int num = sc.nextInt();

        int result = reverse(num);
        System.out.println("Reversed integer: " + result);

        sc.close();
    }
}
