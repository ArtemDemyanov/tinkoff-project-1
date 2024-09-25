package backend.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordManager {
    private final String filePath;
    private final List<String> categories = new ArrayList<>();
    private final PrintStream out;
    private static final String WHITESPACE_REGEX = "\\s+";

    public WordManager(String filePath) {
        this.filePath = filePath;
        this.out = System.out;
        loadCategories();
    }

    private void loadCategories() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(WHITESPACE_REGEX);
                if (parts.length > 0) {
                    categories.add(parts[0]);
                }
            }
        } catch (IOException e) {
            out.println("Ошибка при чтении категорий из файла: " + e.getMessage());
        }
    }

    public String chooseCategory(Scanner scanner) {
        String input;
        int choice;
        Random random = new Random();

        while (true) {
            printCategories();
            out.println("Введите номер категории (1 - " + categories.size()
                + ") или нажмите Enter для случайного выбора: ");
            input = scanner.nextLine();

            if (input.isEmpty()) {
                int randomIndex = random.nextInt(categories.size());
                out.println("Случайно выбрана категория: " + categories.get(randomIndex));
                return categories.get(randomIndex);
            }

            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= categories.size()) {
                    return categories.get(choice - 1);
                } else {
                    out.println("Пожалуйста, введите число от 1 до " + categories.size() + ".");
                }
            } catch (NumberFormatException e) {
                out.println("Неверный ввод. Пожалуйста, введите число.");
            }
        }
    }

    public String chooseWordFromCategory(String category) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(WHITESPACE_REGEX);
                if (parts[0].equalsIgnoreCase(category)) {
                    words.addAll(Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length)));
                    break;
                }
            }
        } catch (IOException e) {
            out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        if (!words.isEmpty()) {
            Random random = new Random();
            return words.get(random.nextInt(words.size())).toLowerCase();
        }
        return null;
    }

    private void printCategories() {
        out.println("Доступные категории:");
        for (int i = 0; i < categories.size(); i++) {
            out.println((i + 1) + ". " + categories.get(i));
        }
    }
}
