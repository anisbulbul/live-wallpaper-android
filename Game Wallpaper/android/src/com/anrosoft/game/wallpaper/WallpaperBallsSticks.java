package com.anrosoft.game.wallpaper;

public class WallpaperBallsSticks extends WallPaperAssetStore {

    public float ballX;
    public float ballY;
    public float ballSpeedX;
    public float ballSpeedY;
    public float ballWidth;
    public float ballAlpha;
    public int ballIndex;

    public WallpaperBallsSticks(float ballX, float ballY,
                                float ballSpeedX, float ballSpeedY,
                                float ballWidth, float ballAlpha, int ballIndex) {
        this.ballX = ballX;
        this.ballY = ballY;
        this.ballSpeedX = ballSpeedX;
        this.ballSpeedY = ballSpeedY;
        this.ballWidth = ballWidth;
        this.ballAlpha = ballAlpha;
        this.ballIndex = ballIndex;
    }
}
