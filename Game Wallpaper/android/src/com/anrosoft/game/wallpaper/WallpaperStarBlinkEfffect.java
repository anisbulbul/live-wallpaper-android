package com.anrosoft.game.wallpaper;

public class WallpaperStarBlinkEfffect extends WallPaperAssetStore {

	public float starX;
	public float starY;
	public float starWidth;
	public float starSpeedX;
	public float starSpeedY;
	public float starAlpha;
	public int starIndex;

	/**
	 * 
	 */
	public WallpaperStarBlinkEfffect(float posX, float posY, float tempWidth, float speedX,
									 float speedY, int starIndex) {
		this.starX = posX;
		this.starY = posY;
		this.starWidth = tempWidth;
		this.starSpeedX = speedX;
		this.starSpeedY = speedY;
		this.starIndex = starIndex;
		this.starAlpha = 1.0f;
	}

}
