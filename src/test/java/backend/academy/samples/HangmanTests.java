package backend.academy.samples;

import backend.academy.GameState;
import backend.academy.WordManager;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

public class HangmanTests {
    @Test
    public void testChooseWordFromCategory() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("input.txt");
        assertNotNull(inputStream, "Файл input.txt не найден");

        WordManager wordManager = new WordManager(inputStream);
        String category = "Animals";
        String word = wordManager.chooseWordFromCategory(category);

        assertNotNull(word, "Слово не должно быть null");
        assertTrue(word.length() > 0, "Длина слова должна быть больше 0");
    }
    @Test
    public void testPrintWordState() {
        GameState gameState = new GameState("cat", 6);
        gameState.processGuess('c');
        gameState.processGuess('a');

        char[] expected = {'c', 'a', '_'};
        assertArrayEquals(expected, gameState.getGuessedWord());
    }
    @Test
    public void testCaseInsensitiveGuessing() {
        GameState gameState = new GameState("cat", 6);
        gameState.processGuess('C');

        assertTrue(gameState.getGuessedLetters().contains('c'));
        assertFalse(gameState.isGameOver());
    }
    @Test
    public void testInvalidWordLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameState("", 6);
        });
    }
    @Test
    public void testGameOverAfterMaxAttempts() {
        GameState gameState = new GameState("cat", 3);

        gameState.processGuess('x');
        gameState.processGuess('y');
        gameState.processGuess('z');

        assertTrue(gameState.isGameOver());
        assertFalse(gameState.isWordGuessed());
    }
    @Test
    public void testGameStateChangeOnGuessing() {
        GameState gameState = new GameState("cat", 6);
        gameState.processGuess('c');
        assertTrue(gameState.getGuessedLetters().contains('c'));
        gameState.processGuess('x');
        assertTrue(gameState.getGuessedLetters().contains('x'));
        assertTrue(gameState.getWrongLetters().contains('x'));
    }
    @Test
    public void testInvalidInputLength() {
        GameState gameState = new GameState("cat", 6);
        String initialGuessedWord = String.valueOf(gameState.getGuessedWord());
        gameState.processGuess('c');
        String stateAfterValidInput = String.valueOf(gameState.getGuessedWord());
        gameState.processGuess(' ');
        String currentGuessedWord = String.valueOf(gameState.getGuessedWord());
        assertEquals(stateAfterValidInput, currentGuessedWord);
        assertEquals(6, gameState.getAttemptsLeft());
        assertFalse(gameState.getGuessedLetters().contains(' '));
        assertFalse(gameState.getWrongLetters().contains(' '));
    }


}
