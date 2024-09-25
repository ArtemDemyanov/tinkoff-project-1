package backend.academy;
import java.util.Random;
import java.util.Scanner;
public class Difficulty {
    private int maxAttempts;
    public Difficulty(Scanner scanner) {
        chooseDifficulty(scanner);
    }
    private void chooseDifficulty(Scanner scanner) {
        System.out.println("Выберите уровень сложности:");
        System.out.println("1. Легкий (10 попыток)");
        System.out.println("2. Средний (6 попыток)");
        System.out.println("3. Сложный (4 попытки)");
        Random random = new Random();
        String input;
        int choice = -1;
        while (true) {
            System.out.print("Введите номер уровня (1-3) или нажмите Enter для случайного выбора: ");
            input = scanner.nextLine();
            if (input.isEmpty()) {
                choice = random.nextInt(3) + 1;
                System.out.println("Случайно выбран уровень сложности: " + choice);
            } else {
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод. Пожалуйста, введите число.");
                    continue;
                }
            }
            if (choice == 1) {
                maxAttempts = 10;
                break;
            } else if (choice == 2) {
                maxAttempts = 6;
                break;
            } else if (choice == 3) {
                maxAttempts = 4;
                break;
            } else {
                System.out.println("Пожалуйста, введите число от 1 до 3.");
            }
        }
    }
    public int getMaxAttempts() {
        return maxAttempts;
    }
}
