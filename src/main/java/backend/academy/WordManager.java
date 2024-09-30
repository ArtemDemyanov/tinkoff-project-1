package backend.academy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordManager {
    private final InputStream inputStream;
    private final List<String> categories = new ArrayList<>();
    private final Map<String, String> wordHints = new HashMap<>();
    private final PrintStream out;
    private static final Pattern LINE_PATTERN = Pattern.compile("^(\\w+)\\s+(.*)$");

    public WordManager(InputStream inputStream) {
        this.inputStream = inputStream;
        this.out = System.out;
        loadCategories();
    }

    private void loadCategories() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher lineMatcher = LINE_PATTERN.matcher(line);
                if (lineMatcher.find()) {
                    String category = lineMatcher.group(1);
                    String wordsHintsString = lineMatcher.group(2);

                    categories.add(category);

                    String[] parts = wordsHintsString.split("\"");
                    for (int i = 0; i < parts.length; i += 2) {
                        if (i + 1 < parts.length) {
                            String word = parts[i].trim();
                            String hint = parts[i + 1].trim();

                            if (!word.isEmpty()) {
                                wordHints.put(word.toLowerCase(), hint);
                            }
                        }
                    }
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
                String randomCategory = categories.get(randomIndex);
                out.println("Случайно выбрана категория: " + randomCategory);
                return chooseWordFromCategory(randomCategory);
            } else {
                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= categories.size()) {
                        String chosenCategory = categories.get(choice - 1);
                        return chooseWordFromCategory(chosenCategory);
                    } else {
                        out.println("Пожалуйста, введите число от 1 до " + categories.size() + ".");
                    }
                } catch (NumberFormatException e) {
                    out.println("Неверный ввод. Пожалуйста, введите число.");
                }
            }
        }
    }

    public String chooseWordFromCategory(String category) {
        List<String> words = new ArrayList<>();
        try (InputStream categoryInputStream = Main.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(categoryInputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher lineMatcher = LINE_PATTERN.matcher(line);
                if (lineMatcher.find() && lineMatcher.group(1).equalsIgnoreCase(category)) {
                    String wordsHintsString = lineMatcher.group(2);

                    String[] parts = wordsHintsString.split("\"");
                    for (int i = 0; i < parts.length; i += 2) {
                        String word = parts[i].trim();
                        if (!word.isEmpty()) {
                            words.add(word.toLowerCase());
                        }
                    }
                    break;
                }
            }

            if (!words.isEmpty()) {
                Random random = new Random();
                return words.get(random.nextInt(words.size()));
            } else {
                out.println("Ошибка: не удалось найти слова для категории " + category);
            }
        } catch (IOException e) {
            out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return null;
    }

    private void printCategories() {
        out.println("Доступные категории:");
        for (int i = 0; i < categories.size(); i++) {
            out.println((i + 1) + ". " + categories.get(i));
        }
    }

    public String getHint(String word) {
        return wordHints.getOrDefault(word.toLowerCase(), "Нет подсказки");
    }
}
