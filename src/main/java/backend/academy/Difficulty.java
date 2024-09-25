package backend.academy;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Difficulty {
    private int maxAttempts;
    private final PrintStream out;

    public Difficulty(Scanner scanner) {
        this.out = System.out;
        chooseDifficulty(scanner);
    }

    private void chooseDifficulty(Scanner scanner) {
        out.println("Выберите уровень сложности:");
        out.println("1. Легкий (10 попыток)");
        out.println("2. Средний (6 попыток)");
        out.println("3. Сложный (4 попытки)");

        Random random = new Random();
        String input;
        int choice;

        while (true) {
            out.print("Введите номер уровня (1-3) или нажмите Enter для случайного выбора: ");
            input = scanner.nextLine();

            if (input.isEmpty()) {
                choice = random.nextInt(3) + 1;
                out.println("Случайно выбран уровень сложности: " + choice);
            } else {
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    out.println("Неверный ввод. Пожалуйста, введите число.");
                    continue;
                }
            }

            if (choice >= 1 && choice <= 3) {
                maxAttempts = switch (choice) {
                    case 1 -> 10;
                    case 2 -> 6;
                    default -> 4;
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
