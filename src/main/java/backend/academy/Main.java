package backend.academy;

import java.io.InputStream;
import java.io.PrintStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    private final PrintStream out = System.out;

    public static void main(String[] args) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("input.txt");
        if (inputStream == null) {
            out.println("Файл input.txt не найден");
            return;
        }

        HangmanGame game = new HangmanGame(inputStream);
        game.play();
    }
}
