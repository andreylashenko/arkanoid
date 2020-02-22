package core;

import java.awt.*;

public class Text {

    public void score(Graphics graphics, int score) {
        render(graphics, "" + score, Color.WHITE, 900, 30, 25);
    }

    public void startGame(Graphics graphics) {
        render(graphics, "Press whitespace to start", Color.RED, 150, 400, 50);
    }

    public void gameOver(Graphics graphics) {
        render(graphics, "Game over. Press Enter to play again", Color.RED, 80, 400, 40);
    }

    public void victory(Graphics graphics) {
        render(graphics, "You won. Press Enter to play again", Color.RED, 80, 400, 40);
    }

    private void render(Graphics g, String text, Color color, int x, int y, int size) {
        g.setColor(color);
        g.setFont(new Font("serif", Font.BOLD, size));
        g.drawString(text, x, y);
    }
}
