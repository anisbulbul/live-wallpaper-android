// (c) anrosoft.com

package com.anrosoft.game.wallpaper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class WallpaperView extends WallPaperAssetStore implements Screen {

    private Wallpaper3D game;
    private SpriteBatch spriteBatch;

    int touchCount = 0;

    public WallpaperView(final Wallpaper3D game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    private void drawScreen(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.enableBlending();

        drawBallsAndSticks();
        drawStarBlinkEffects();

//        drawDebug();

        spriteBatch.end();
        spriteBatch.disableBlending();

    }

    private void drawDebug() {

    }

    private void drawStarBlinkEffects() {

        for (int i = 0; i < starGlows.size(); i++) {

            Color c = spriteBatch.getColor();
            spriteBatch.setColor(c.r, c.g, c.b, starGlows.get(i).starAlpha);

            spriteBatch.draw(starGlowsTexture[starGlows.get(i).starIndex],
                    starGlows.get(i).starX, starGlows.get(i).starY,
                    starGlows.get(i).starWidth, starGlows.get(i).starWidth);
            spriteBatch.setColor(c.r, c.g, c.b, 1.0f); // set alpha to 1

            starGlows.get(i).starX += starGlows.get(i).starSpeedX;
            starGlows.get(i).starY += starGlows.get(i).starSpeedY;
            starGlows.get(i).starWidth -= 0.3f;
            starGlows.get(i).starAlpha -= 0.01f;

            int index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            starGlows.get(i).starIndex = index;
            if ((starGlows.get(i).starAlpha <= 0)
                    || (starGlows.get(i).starX + starGlows.get(i).starWidth < 0 || starGlows
                    .get(i).starX > width)
                    || (starGlows.get(i).starY + starGlows.get(i).starWidth < 0 || starGlows
                    .get(i).starY > height)) {
                starGlows.remove(i);
            }

        }


    }

    private void updateScreen(float delta) {
        if (Gdx.input.justTouched()) {
            touchCount -= 100;
            float getX = Gdx.input.getX();
            float getY = height - Gdx.input.getY();
            for (int i = 0; i < ballsSticks.size(); i++) {
                if (CollisionCheck.isCollisionOccuredCircleToPoint(ballsSticks.get(i).ballX + ballsSticks.get(i).ballWidth / 2, ballsSticks.get(i).ballY + ballsSticks.get(i).ballWidth / 2, ballsSticks.get(i).ballWidth / 2,
                        getX, getY)) {
                    addStarBlinkEffects(ballsSticks.get(i).ballX, ballsSticks.get(i).ballY);
                    if (isSoundEnable) {
                        touchSound.play();
                    }
                    reloadBallStatus(i);
                    touchCount++;
                    break;

                }
            }
        }
    }

    @Override
    public void render(float delta) {
        updateScreen(delta);
        drawScreen(delta);
    }

    private void reloadBallStatus(int i) {
        random = new Random();
        boolean vertical = (random.nextInt() < 0) ? false : true;
        int up = (random.nextInt() < 0) ? -1 : 1;
        float speedRatio = Math.abs(random.nextFloat() % 1) + 1;
        if (vertical) {
            ballsSticks.get(i).ballSpeedX = 0;
            ballsSticks.get(i).ballSpeedY = speedRatio * BALLS_SPEED_Y * up;
            ballsSticks.get(i).ballY = height / 2 - height * up;
            ballsSticks.get(i).ballX = Math.abs(new Random().nextInt((int) (width - BALL_WIDTH))) % ((width - BALL_WIDTH));
        } else {
            ballsSticks.get(i).ballSpeedX = speedRatio * BALLS_SPEED_X * up;
            ballsSticks.get(i).ballX = width / 2 - width * up;
            ballsSticks.get(i).ballSpeedY = 0;
            ballsSticks.get(i).ballY = Math.abs(new Random().nextInt((int) (height - BALL_WIDTH))) % ((height - BALL_WIDTH));
        }
    }

    private void drawBallsAndSticks() {
        for (int i = 0; i < ballsSticks.size(); i++) {
            spriteBatch.draw(ballTexture[ballsSticks.get(i).ballIndex], ballsSticks.get(i).ballX, ballsSticks.get(i).ballY, ballsSticks.get(i).ballWidth, ballsSticks.get(i).ballWidth);
            ballsSticks.get(i).ballX += BALLS_SPEED_RATIO * ballsSticks.get(i).ballSpeedX;
            ballsSticks.get(i).ballY += BALLS_SPEED_RATIO * ballsSticks.get(i).ballSpeedY;
            if ((ballsSticks.get(i).ballX < -width / 2 || ballsSticks.get(i).ballX > 3 * width / 2) || (ballsSticks.get(i).ballY < -height / 2 || ballsSticks.get(i).ballY > 3 * height / 2)) {
                reloadBallStatus(i);
            } else {
                for (int k = i + 1; k < ballsSticks.size(); k++) {
                    if (CollisionCheck.isCollisionOccuredCircleToCircle(ballsSticks.get(i).ballX + ballsSticks.get(i).ballWidth / 2, ballsSticks.get(i).ballY + ballsSticks.get(i).ballWidth / 2, ballsSticks.get(i).ballWidth / 4,
                            ballsSticks.get(k).ballX + ballsSticks.get(k).ballWidth / 2, ballsSticks.get(k).ballY + ballsSticks.get(k).ballWidth / 2, ballsSticks.get(k).ballWidth / 4)) {
                        addStarBlinkEffects(ballsSticks.get(i).ballX, ballsSticks.get(i).ballY);
                        if (isSoundEnable && ballsSticks.get(k).ballX > 0 && ballsSticks.get(k).ballX + ballsSticks.get(k).ballWidth < width &&
                                ballsSticks.get(k).ballY > 0 && ballsSticks.get(k).ballY + ballsSticks.get(k).ballWidth < height) {
                            collisionSound.play();
                        }
                        reloadBallStatus(i);
                        reloadBallStatus(k);
                        touchCount++;
                        break;

                    }
                }
            }
        }
    }

    private void addStarBlinkEffects(float ballX, float ballY) {

        for (float i = 1.0f; i <= 3; i++) {
            int index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            float tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, -tempSpeed, 0,
                    index));
            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, -tempSpeed
                    / SQRT_OF_2, tempSpeed / SQRT_OF_2, index));
            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, 0, tempSpeed,
                    index));
            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, 0, -tempSpeed,
                    index));
            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, -tempSpeed
                    / SQRT_OF_2, -tempSpeed / SQRT_OF_2, index));

            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, tempSpeed
                    / SQRT_OF_2, tempSpeed / SQRT_OF_2, index));

            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, tempSpeed
                    / SQRT_OF_2, -tempSpeed / SQRT_OF_2, index));

            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, tempSpeed, 0,
                    index));

            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, tempSpeed,
                    -tempSpeed / (SQRT_OF_2 * 2), index));

            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, -tempSpeed,
                    -tempSpeed / (SQRT_OF_2 * 2), index));

            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, -tempSpeed
                    / (SQRT_OF_2 * 2), -tempSpeed, index));

            index = (Math.abs(new Random().nextInt(100)))
                    % NUMBER_OF_STAR_GLOWS;
            tempSpeed = (Math.abs(new Random().nextInt((int) STAR_SPEED)))
                    + STAR_SPEED * i / 2;
            starGlows.add(new WallpaperStarBlinkEfffect(ballX, ballY, STAR_MAX_WIDTH, -tempSpeed
                    / (SQRT_OF_2 * 2), tempSpeed, index));
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }
}