package entity;

import core.Game;
import settings.Settings;

import java.awt.*;

public class BlockBreaker {

    public static final int WIDTH = 150, HEIGHT = 10, Y = 700;
    public static final int START_X_POSITION = Settings.GAME_WIDTH / 2 - WIDTH / 2;
    public boolean isGoingLeft = false;
    public boolean isGoingRight = false;

    private int x;
    private int xMove;
    private int speed = 5;

    private Game game;

    public BlockBreaker(Game game, int x) {
        this.x = x;
        this.game = game;
    }

    public Rectangle getRect() {
        return new Rectangle(x, Y, WIDTH, HEIGHT);
    }

    public void tick() {
        getInput();
        move();
    }

    private void getInput() {
        xMove = 0;

        if (game.getKeyManager().left) {
            xMove = -speed;
            isGoingLeft = true;
            isGoingRight = false;
        }

        if (game.getKeyManager().right) {
            xMove = speed;
            isGoingRight = true;
            isGoingLeft = false;
        }
    }

    public void move() {

        if (x > 840) {
            x = 840;
        }

        if (x < 5) {
            x = 5;
        }

        if (x >= 1 && x <= 840) {
            x += xMove;
        } else {
            x -= xMove;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, Y, WIDTH, HEIGHT);
        g.drawRect(x, Y, WIDTH, HEIGHT);
    }
}
