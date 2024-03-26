package Generic.Utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings({"DataFlowIssue", "unused"})
public class Input {
    static Scanner s = new Scanner(System.in);
    static boolean exceptionCaught;

//    Methods to implement when catching InputMismatchException
    public static byte readByte(String message){
        byte answer = 0;

        do {
            try{
                System.out.println(message);
                answer = s.nextByte();
                exceptionCaught = false;
            } catch (InputMismatchException ignored) {
                System.out.println("A whole number ranging from -128 to 127 is expected.");
                s.nextLine();
                exceptionCaught = true;
            }
        } while(exceptionCaught);

        return answer;
    }
    public static int readInt(String message){
        int answer = 0;

        do {
            try{
                System.out.println(message);
                answer = s.nextInt();
                exceptionCaught = false;
            } catch (InputMismatchException ignored) {
                System.out.println("A whole number ranging from -2147483648 to 2147483647 is expected");
                s.nextLine();
                exceptionCaught = true;
            }
        } while(exceptionCaught);

        return answer;
    }
    public static float readFloat(String message){
        float answer = 0;

        do {
            try{
                System.out.println(message);
                answer = s.nextFloat();
                exceptionCaught = false;
            } catch (InputMismatchException ignored) {
                System.out.println("A fractional number ranging from from 3.4e−038 to 3.4e+038 is expected");
                s.nextLine();
                exceptionCaught = true;
            }
        } while(exceptionCaught);

        return answer;
    }
    public static double readDouble(String message){
        double answer = 0;

        do {
            try{
                System.out.println(message);
                answer = s.nextDouble();
                exceptionCaught = false;
            } catch (InputMismatchException ignored) {
                System.out.println("A fractional number ranging from 1.7e−308 to 1.7e+308 is expected");
                s.nextLine();
                exceptionCaught = true;
            }
        } while(exceptionCaught);

        return answer;
    }

//    Methods to implement when catching exceptions from the exception class
    public static char readChar(String message){
        char answer = ' ';

        do {
            try{
                System.out.println(message);
                answer = s.next().charAt(0);
                exceptionCaught = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                s.nextLine();
                exceptionCaught = true;
            }
        } while(exceptionCaught);

        return answer;
    }
    public static String readString(String message){
        String answer = "";

        do {
            try{
                System.out.println(message);
                s.nextLine();
                answer = s.nextLine();
                exceptionCaught = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                exceptionCaught = true;
            }
        } while(exceptionCaught);

        return answer;
    }
    public static boolean readIfNo (String message){
        String tempAnswer;
        boolean answer = false;

        do {
            try{
                System.out.println(message);
                tempAnswer = s.next();
                exceptionCaught = false;

                if(tempAnswer.equalsIgnoreCase("y") || tempAnswer.equalsIgnoreCase("true")) {
                    answer = true;
                } else if (tempAnswer.equalsIgnoreCase("n") || tempAnswer.equalsIgnoreCase("false")) {
                    answer = false;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                s.nextLine();
                exceptionCaught = true;
            }
        } while(exceptionCaught);

        return answer;
    }
}
