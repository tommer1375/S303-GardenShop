package Generic.Utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Input {

    public static byte readByte(String message) {
        Scanner scanner = new Scanner(System.in);
        byte answer;

        while (true) {
            try {
                System.out.println(message);
                answer = scanner.nextByte();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid byte value.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
        return answer;
    }

    public static int readInt(String message) {
        Scanner scanner = new Scanner(System.in);
        int answer;

        while (true) {
            try {
                System.out.println(message);
                answer = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer value.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
        return answer;
    }

    public static float readFloat(String message) {
        Scanner scanner = new Scanner(System.in);
        float answer;

        while (true) {
            try {
                System.out.println(message);
                answer = scanner.nextFloat();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid float value.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
        return answer;
    }

    public static double readDouble(String message) {
        Scanner scanner = new Scanner(System.in);
        double answer;

        while (true) {
            try {
                System.out.println(message);
                answer = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid double value.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
        return answer;
    }

    public static char readChar(String message) {
        Scanner scanner = new Scanner(System.in);
        char answer;

        while (true) {
            try {
                System.out.println(message);
                answer = scanner.next().charAt(0);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid character.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
        return answer;
    }

    public static String readString(String message) {
        Scanner scanner = new Scanner(System.in);
        String answer;

        while (true) {
            try {
                System.out.println(message);
                answer = scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid string.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
        return answer;
    }

    public static boolean readBoolean(String message) {
        Scanner scanner = new Scanner(System.in);
        boolean answer;

        while (true) {
            try {
                System.out.println(message);
                String input = scanner.next().toLowerCase();
                if (input.equals("y") || input.equals("yes")) {
                    answer = true;
                } else if (input.equals("n") || input.equals("no")) {
                    answer = false;
                } else {
                    throw new InputMismatchException("Error: Please enter 'y' or 'n'.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
        return answer;
    }
}