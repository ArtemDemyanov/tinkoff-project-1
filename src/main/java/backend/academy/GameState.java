package backend.academy;
import java.util.*;
public class GameState {
    private String chosenWord;
    private char[] guessedWord;
    private int attemptsLeft;
    private Set<Character> guessedLetters = new HashSet<>();
    private Set<Character> wrongLetters = new HashSet<>();
    private int maxAttempts;
    public GameState(String chosenWord, int maxAttempts) {
        if (chosenWord == null || chosenWord.length() < 2) {
            throw new IllegalArgumentException("Слово должно содержать как минимум 2 символа.");
        }
        this.chosenWord = chosenWord;
        this.guessedWord = new char[chosenWord.length()];
        Arrays.fill(guessedWord, '_');
        this.maxAttempts = maxAttempts;
        this.attemptsLeft = maxAttempts;
    }
    public void processGuess(char guess) {
        guess = Character.toLowerCase(guess);
        if (Character.isWhitespace(guess) || String.valueOf(guess).length() > 1) {
            System.out.println("Введите одну букву.");
            return;
        }
        if (guessedLetters.contains(guess) || wrongLetters.contains(guess)) {
            System.out.println("Эта буква уже была угадана.");
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
            case 0 -> System.out.println("""
            +---+
            |   |
                |
                |
                |
                |
            =========""");
            case 1 -> System.out.println("""
            +---+
            |   |
            O   |
                |
                |
                |
            =========""");
            case 2 -> System.out.println("""
            +---+
            |   |
            O   |
            |   |
                |
                |
            =========""");
            case 3 -> System.out.println("""
            +---+
            |   |
            O   |
           /|   |
                |
                |
            =========""");
            case 4 -> System.out.println("""
            +---+
            |   |
            O   |
           /|\\  |
                |
                |
            =========""");
            case 5 -> System.out.println("""
            +---+
            |   |
            O   |
           /|\\  |
           /    |
                |
            =========""");
            case 6 -> System.out.println("""
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
        System.out.println("Слово: " + String.valueOf(guessedWord));
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
        return guessedLetters;
    }
    public void printResult() {
        if (isWordGuessed()) {
            System.out.println("Поздравляем! Вы угадали слово: " + chosenWord);
        } else {
            printHangman();
            System.out.println("Вы проиграли! Загаданное слово было: " + chosenWord);
        }
    }
}
