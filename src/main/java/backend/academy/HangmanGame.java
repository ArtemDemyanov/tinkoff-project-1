package backend.academy;

import java.io.PrintStream;
import java.util.Scanner;

public class HangmanGame {
    private final WordManager wordManager;
    private final Difficulty difficulty;
    private GameState gameState;
    private final PrintStream out;

    public HangmanGame(String filePath) {
        this.wordManager = new WordManager(filePath);
        this.out = System.out;
        Scanner scanner = new Scanner(System.in);
        this.difficulty = new Difficulty(scanner);
        String chosenCategory = wordManager.chooseCategory(scanner);
        out.println("Категория: " + chosenCategory);
        String chosenWord = wordManager.chooseWordFromCategory(chosenCategory);
        if (chosenWord != null) {
            this.gameState = new GameState(chosenWord, difficulty.getMaxAttempts());
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
            out.println("Угадайте букву:");
            out.println("Оставшиеся попытки: " + gameState.getAttemptsLeft());
            out.println("Использованные буквы: " + gameState.getGuessedLetters());

            String input = scanner.nextLine().toLowerCase();
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

    public static void main(String[] args) {
        HangmanGame game = new HangmanGame("path/to/your/words.txt");
        game.play();
    }
}
