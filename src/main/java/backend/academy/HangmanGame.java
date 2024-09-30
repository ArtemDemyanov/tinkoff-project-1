package backend.academy;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class HangmanGame {
    private final WordManager wordManager;
    private final Difficulty difficulty;
    private GameState gameState;
    private final PrintStream out;
    private String chosenWord;

    public HangmanGame(InputStream inputStream) {
        this.wordManager = new WordManager(inputStream);
        this.out = System.out;
        Scanner scanner = new Scanner(System.in);
        this.difficulty = new Difficulty(scanner, out);
        this.chosenWord = wordManager.chooseCategory(scanner);

        if (chosenWord != null) {
            out.println("Начинаем игру с выбранным словом.");
            this.gameState = new GameState(chosenWord, difficulty.getMaxAttempts());
        } else {
            out.println("Не удалось выбрать слово для игры. Завершение работы.");
        }
    }

    public void play() {
        if (gameState == null) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (!gameState.isGameOver()) {
            gameState.printHangman();
            gameState.printWordState();
            out.println("Угадайте букву или введите '9' для получения подсказки:");
            out.println("Оставшиеся попытки: " + gameState.getAttemptsLeft());
            out.println("Использованные буквы: " + gameState.getGuessedLetters());

            String input = scanner.nextLine().toLowerCase();

            if (input.equals("9")) {
                String hint = wordManager.getHint(chosenWord);
                out.println("Подсказка для слова: " + hint);
                continue;
            }

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                out.println("Пожалуйста, введите одну букву.");
                continue;
            }

            char guess = input.charAt(0);
            gameState.processGuess(guess);
        }

        gameState.printResult();
        scanner.close();
    }
}
