package com.anrosoft.game.wallpaper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by anrosoft on 7/23/2015.
 */
public class WallPaperAssetStore {

    public static float width = Gdx.graphics.getWidth();
    public static float height = Gdx.graphics.getHeight();
    public static final int NUMBER_OF_STAR_GLOWS = 15;
    public static final int NUMBER_OF_BALLS = 3;
    public static final float BALL_WIDTH = width / 5;
    public static final float BALLS_SPEED_X = width / 200;
    public static final float BALLS_SPEED_Y = height / 200;
    public static final float STAR_MAX_WIDTH = width / 10;
    public static final float STAR_SPEED = width / 200;
    public static final float SQRT_OF_2 = 1.414213f;

    public static boolean isSoundEnable = true;

    public static float BALLS_SPEED_RATIO = 1;

    public static Sound collisionSound;
    public static Sound touchSound;

    public static Texture[] starGlowsTexture;
    public static Texture[] ballTexture;
    public static Random random;

    public static ArrayList<WallpaperStarBlinkEfffect> starGlows;
    public static ArrayList<WallpaperBallsSticks> ballsSticks;

    public static void loadAssets() {

        starGlows = new ArrayList<WallpaperStarBlinkEfffect>();
        starGlows.clear();
        ballsSticks = new ArrayList<WallpaperBallsSticks>();
        ballsSticks.clear();
        random = new Random();

        collisionSound = initSound("sounds/collision.mp3");
        touchSound = initSound("sounds/touch.wav");
        isSoundEnable = true;

        ballTexture = new Texture[NUMBER_OF_BALLS];
        for (int i = 0; i < NUMBER_OF_BALLS; i++) {
            ballTexture[i] = initTexture("balls/ball" + (i + 1) + ".png");
        }

        starGlowsTexture = new Texture[NUMBER_OF_STAR_GLOWS];
        for (int i = 0; i < NUMBER_OF_STAR_GLOWS; i++) {
            starGlowsTexture[i] = initTexture("stars/star" + (i + 1) + ".png");
        }

        for (int i = 0; i < NUMBER_OF_BALLS * 2; i++) {
            float posX = Math.abs(new Random().nextInt((int) (width - BALL_WIDTH))) % ((width - BALL_WIDTH));
            float posY = Math.abs(new Random().nextInt((int) (height - BALL_WIDTH))) % ((height - BALL_WIDTH));
            float widthTemp = BALL_WIDTH;
            float speedX = BALLS_SPEED_X;
            float speedY = BALLS_SPEED_Y;
            float alpha = 1.0f;
            random = new Random();
            int indexTemp = Math.abs(random.nextInt(10)) % NUMBER_OF_BALLS;
            ballsSticks.add(new WallpaperBallsSticks(posX, posY, speedX, speedY, widthTemp, alpha, indexTemp));
        }

    }

    public static Texture initTexture(String path) {
        return new Texture(Gdx.files.internal(path));
    }

    public static Sound initSound(String path) {
        return Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public static BitmapFont initFont(String fnt, String png) {
        return new BitmapFont(Gdx.files.internal(fnt), Gdx.files.internal(png),
                false);
    }
}
