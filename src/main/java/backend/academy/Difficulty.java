package backend.academy;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Difficulty {
    private int maxAttempts;
    private final PrintStream out;
    private static final int EASY_ATTEMPTS = 10;
    private static final int MEDIUM_ATTEMPTS = 6;
    private static final int HARD_ATTEMPTS = 4;
    private static final int RANDOM_LEVELS = 3;
    private static final String ATTEMPTS_SUFFIX = " попыток)";

    public Difficulty(Scanner scanner) {
        this.out = System.out;
        chooseDifficulty(scanner);
    }

    private void chooseDifficulty(Scanner scanner) {
        out.println("Выберите уровень сложности:");
        out.println("1. Легкий (" + EASY_ATTEMPTS + ATTEMPTS_SUFFIX);
        out.println("2. Средний (" + MEDIUM_ATTEMPTS + ATTEMPTS_SUFFIX);
        out.println("3. Сложный (" + HARD_ATTEMPTS + " попытки)");

        Random random = new Random();
        String input;
        int choice;

        while (true) {
            out.print("Введите номер уровня (1-3) или нажмите Enter для случайного выбора: ");
            input = scanner.nextLine();

            if (input.isEmpty()) {
                choice = random.nextInt(RANDOM_LEVELS) + 1;
                out.println("Случайно выбран уровень сложности: " + choice);
            } else {
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    out.println("Неверный ввод. Пожалуйста, введите число.");
                    continue;
                }
            }

            if (choice >= 1 && choice <= RANDOM_LEVELS) {
                maxAttempts = switch (choice) {
                    case 1 -> EASY_ATTEMPTS;
                    case 2 -> MEDIUM_ATTEMPTS;
                    default -> HARD_ATTEMPTS;
                };
                break;
            } else {
                out.println("Пожалуйста, введите число от 1 до 3.");
            }
        }
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
