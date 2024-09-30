package backend.academy;

import lombok.experimental.UtilityClass;
import java.io.InputStream;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("input.txt");
        if (inputStream == null) {
            System.out.println("Файл input.txt не найден");
            return;
        }

        HangmanGame game = new HangmanGame(inputStream);
        game.play();
    }
}
