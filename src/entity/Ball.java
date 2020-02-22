package entity;

import core.Game;
import settings.Settings;

import java.awt.*;

public class Ball {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int START_X_POSITION = Settings.GAME_WIDTH / 2 - WIDTH / 2;
    public static final int START_Y_POSITION = 600;

    public int ballYdir = -5;
    public int ballXdir = -3;

    private int x;
    private int y;

    private Game game;

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public Ball(Game game, int x, int y) {
        this.x = x;
        this.y = y;
        this.game = game;
    }


    public void tick() {
        move();
    }

    private void move() {
        if (game.getKeyManager().space && game.isGameBegin) {
            game.isGameBegin = true;
            x += ballXdir;
            y += ballYdir;

            if (x < 0) {
                ballXdir = -ballXdir;
            }

            if (y < 0) {
                ballYdir = -ballYdir;
            }

            if (x > 980) {
                ballXdir = -ballXdir;
            }

            if(y > 800) {
                game.isGameOver = true;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.drawOval(x, y, WIDTH, HEIGHT);
    }
}
