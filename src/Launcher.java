import core.Game;
import settings.Settings;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Arkanoid game", Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        game.start();
    }
}
