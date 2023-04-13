import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean success = false;
        System.out.println("Hello, welcome to BigInt Calculator");
        BigInt firstNum = null;
        BigInt secondNum = null;
        while (!success) {
            try {
                System.out.println("Please enter first number");
                firstNum = new BigInt(scanner.nextLine());
                success = true;
            } catch (Exception e) {
                System.out.println("Error message: " + e.getMessage());
            }
        }
        success = false;

        while (!success) {
            try {
                System.out.println("Please enter second number");
                secondNum = new BigInt(scanner.nextLine());
                success = true;
            } catch (Exception e) {
                System.out.println("Error message: " + e.getMessage());
            }
        }
        try {
            System.out.println("(" + firstNum + ")+(" + secondNum + ")=" + firstNum.plus(secondNum));
            System.out.println("(" + firstNum + ")-(" + secondNum + ")=" + firstNum.minus(secondNum));
            System.out.println("(" + firstNum + ")*(" + secondNum + ")=" + firstNum.multiply(secondNum));
            System.out.println("(" + firstNum + ")/(" + secondNum + ")=" + firstNum.divide(secondNum));
            System.out.println("(" + firstNum + ")==(" + secondNum + ") =" + firstNum.equals(secondNum));
            System.out.println("(" + firstNum + ") compareTo (" + secondNum + ") " + firstNum.compareTo(secondNum));
        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
        }
    }
}

