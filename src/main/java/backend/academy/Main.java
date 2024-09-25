package backend.academy;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        HangmanGame game = new HangmanGame("src/main/resources/input.txt");
        game.play();
    }
}
