package backend.academy;
import java.io.*;
import java.util.*;
public class WordManager {
    private String filePath;
    private List<String> categories = new ArrayList<>();
    public WordManager(String filePath) {
        this.filePath = filePath;
        loadCategories();
    }
    private void loadCategories() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length > 0) {
                    categories.add(parts[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении категорий из файла: " + e.getMessage());
        }
    }
    public String chooseCategory(Scanner scanner) {
        String input;
        int choice = -1;
        Random random = new Random();
        while (true) {
            printCategories();
            System.out.println("Введите номер категории (1 - " + categories.size() + ") или нажмите Enter для случайного выбора: ");
            input = scanner.nextLine();
            if (input.isEmpty()) {
                int randomIndex = random.nextInt(categories.size());
                System.out.println("Случайно выбрана категория: " + categories.get(randomIndex));
                return categories.get(randomIndex);
            }
            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= categories.size()) {
                    return categories.get(choice - 1);
                } else {
                    System.out.println("Пожалуйста, введите число от 1 до " + categories.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
            }
        }
    }
    public String chooseWordFromCategory(String category) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts[0].equalsIgnoreCase(category)) {
                    words.addAll(Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length)));
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        if (!words.isEmpty()) {
            Random random = new Random();
            return words.get(random.nextInt(words.size())).toLowerCase();
        }
        return null;
    }
    private void printCategories() {
        System.out.println("Доступные категории:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
    }
}
