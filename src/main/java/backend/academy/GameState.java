package backend.academy;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameState {
    private final String chosenWord;
    private final char[] guessedWord;
    private int attemptsLeft;
    private final Set<Character> guessedLetters = new HashSet<>();
    private final Set<Character> wrongLetters = new HashSet<>();
    private final int maxAttempts;
    private final PrintStream out;

    public GameState(String chosenWord, int maxAttempts) {
        if (chosenWord == null || chosenWord.length() < 2) {
            throw new IllegalArgumentException("Слово должно содержать как минимум 2 символа.");
        }
        this.chosenWord = chosenWord;
        this.guessedWord = new char[chosenWord.length()];
        Arrays.fill(guessedWord, '_');
        this.maxAttempts = maxAttempts;
        this.attemptsLeft = maxAttempts;
        this.out = System.out;
    }

    public void processGuess(char guess) {
        guess = Character.toLowerCase(guess);
        if (Character.isWhitespace(guess) || String.valueOf(guess).length() > 1) {
            out.println("Введите одну букву.");
            return;
        }
        if (guessedLetters.contains(guess) || wrongLetters.contains(guess)) {
            out.println("Эта буква уже была угадана.");
            return;
        }
        guessedLetters.add(guess);

        if (chosenWord.indexOf(guess) >= 0) {
            updateGuessedWord(guess);
        } else {
            wrongLetters.add(guess);
            attemptsLeft--;
        }
    }

    private void updateGuessedWord(char guess) {
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == guess) {
                guessedWord[i] = guess;
            }
        }
    }

    public boolean isGameOver() {
        return attemptsLeft <= 0 || isWordGuessed();
    }

    public boolean isWordGuessed() {
        return Arrays.equals(guessedWord, chosenWord.toCharArray());
    }

    public void printHangman() {
        int stage = (maxAttempts - attemptsLeft) * 6 / maxAttempts;
        switch (stage) {
            case 0 -> out.println("""
            +---+
            |   |
                |
                |
                |
                |
            =========""");
            case 1 -> out.println("""
            +---+
            |   |
            O   |
                |
                |
                |
            =========""");
            case 2 -> out.println("""
            +---+
            |   |
            O   |
            |   |
                |
                |
            =========""");
            case 3 -> out.println("""
            +---+
            |   |
            O   |
           /|   |
                |
                |
            =========""");
            case 4 -> out.println("""
            +---+
            |   |
            O   |
           /|\\  |
                |
                |
            =========""");
            case 5 -> out.println("""
            +---+
            |   |
            O   |
           /|\\  |
           /    |
                |
            =========""");
            case 6 -> out.println("""
            +---+
            |   |
            O   |
           /|\\  |
           / \\  |
                |
            =========""");
        }
    }

    public void printWordState() {
        out.println("Слово: " + String.valueOf(guessedWord));
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public char[] getGuessedWord() {
        return guessedWord;
    }

    public Set<Character> getWrongLetters() {
        return wrongLetters;
    }

    public void printResult() {
        if (isWordGuessed()) {
            out.println("Поздравляем! Вы угадали слово: " + chosenWord);
        } else {
            printHangman();
            out.println("Вы проиграли! Загаданное слово было: " + chosenWord);
        }
    }
}
