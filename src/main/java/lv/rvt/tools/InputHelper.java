package lv.rvt.tools;
import java.util.Scanner;
import java.util.function.IntPredicate;

public class InputHelper {

    public static int getIntInput(Scanner scanner,
                                  String prompt,
                                  IntPredicate validator,
                                  String invalidMessage) {
        int input;
        boolean firstError = false;

        while (true) {
            if (firstError) {
                System.out.print("\033[F\033[2K"); 
                System.out.print("\033[F\033[2K");
                System.out.print(invalidMessage + " ");
            } else {
                System.out.print(prompt);
            }

            String line = scanner.nextLine();

            try {
                input = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Nepareiza ievade! Lūdzu, ievadiet veselu skaitli.");
                firstError = true;
                continue;
            }

            if (!validator.test(input)) {
                System.out.println(invalidMessage);
                firstError = true;
                continue;
            }

            return input;
        }
    }

    public static int getIntInput(Scanner scanner,
                                  String prompt,
                                  int min,
                                  int max) {
        String invalidMsg = String.format(
            "Nepareiza izvēle! Lūdzu, ievadiet skaitli no %d līdz %d.", min, max);
        return getIntInput(scanner, prompt, 
                            i -> i >= min && i <= max, 
                            invalidMsg);
    }
}