package backend.academy;

import java.util.Scanner;

public class HangmanGame {
    private final WordManager wordManager;
    private final Difficulty difficulty;
    private GameState gameState;

    public HangmanGame(String filePath) {
        wordManager = new WordManager(filePath);
        Scanner scanner = new Scanner(System.in);
        difficulty = new Difficulty(scanner);
        String chosenCategory = wordManager.chooseCategory(scanner);
        System.out.println("Категория: " + chosenCategory);
        String chosenWord = wordManager.chooseWordFromCategory(chosenCategory);
        if (chosenWord != null) {
            gameState = new GameState(chosenWord, difficulty.getMaxAttempts());
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
            System.out.println("Угадайте букву:");
            System.out.println("Оставшиеся попытки: " + gameState.getAttemptsLeft());
            System.out.println("Использованные буквы: " + gameState.getGuessedLetters());

            String input = scanner.nextLine().toLowerCase();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Пожалуйста, введите одну букву.");
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
