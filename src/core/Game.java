package core;

import display.Display;
import entity.Ball;
import entity.Block;
import entity.BlockBreaker;
import keyManager.KeyManager;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game implements Runnable {

    private Display display;
    private Thread thread;
    private Text text;
    private int score;
    private boolean running;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    public String title;
    public int width, height;
    public boolean isGameBegin;
    public boolean isGameOver;

    int blockYPosition = 10;
    int blockXPosition = 0;

    ArrayList<Block> blocks = new ArrayList<>();

    public BlockBreaker blockBreaker;
    private Ball ball;

    private KeyManager keyManager;

    private void blockDrawing(int blockYPosition, int blockXPosition) {
        for (int i = 0; i < 35; i++) {

            if (i % 7 == 0) {
                blockYPosition += Block.HEIGHT + 5;
                blockXPosition = 0;
            }

            blockXPosition += Block.WIDTH;
            blocks.add(new Block( blockXPosition, blockYPosition));
            blockXPosition += 5;
        }
    }

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

    }

    //Главный цикл игры, исполняется 60 раз в секунду
    @Override
    public void run() {
        display = new Display(title, width, height);
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                //на каждой итерации обновляем объекты на сцене
                tick();
                //на каждой итерации отрисовываем объекты на сцене
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000) {
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }
    //Обновленине содержимого сцены
    private void tick() {
        keyManager.tick();
        ball.tick();
        blockBreaker.tick();

        if (isGameOver) {
            isGameBegin = false;
        }

        if (blockBreaker.getRect().intersects(ball.getRect())) {
            ball.ballYdir = -ball.ballYdir;
            if (blockBreaker.isGoingRight) {
                ball.ballXdir = +ball.ballXdir;
            }

            if (blockBreaker.isGoingLeft) {
                ball.ballXdir = -ball.ballXdir;
            }
        }

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getRect().intersects(ball.getRect())){
                blocks.remove(blocks.get(i));
                ball.ballYdir = -ball.ballYdir;
                score += 5;
            }
        }

        if (keyManager.enter) {
            blocks.clear();
            init();
        }
    }

    //Отрисовка содержимого сцены
    private void render() {

        bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).render(graphics);
        }

        if (isGameBegin && !keyManager.space) {
            text.startGame(graphics);
        }

        if (isGameOver && blocks.size() > 0) {
            text.gameOver(graphics);
        }

        if(blocks.size() == 0) {
            text.victory(graphics);
        }

        text.score(graphics, score);
        blockBreaker.render(graphics);
        ball.render(graphics);

        bufferStrategy.show();
        graphics.dispose();
    }

    //инициализация сцены
    private void init() {
        blockDrawing(blockYPosition, blockXPosition);

        keyManager = new KeyManager();
        text = new Text();
        blockBreaker = new BlockBreaker(this, BlockBreaker.START_X_POSITION);
        ball = new Ball(this, Ball.START_X_POSITION, Ball.START_Y_POSITION);
        isGameBegin = true;
        isGameOver = false;
        score = 0;
        display.getFrame().addKeyListener(keyManager);
    }

    //старт игры
    public synchronized void start() {
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    //остановка игры
    public synchronized void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

}
