package com.anrosoft.game.wallpaper;

/**
 * Created by anisbulbul on 7/23/2015.
 */
public class CollisionCheck {

    public static boolean isCollisionOccuredCircleToCircle(float centerX1, float centerY1, float radius1, float centerX2, float centerY2, float radius2) {
        double centerDistance = Math.sqrt((centerX1 - centerX2) * (centerX1 - centerX2) + (centerY1 - centerY2) * (centerY1 - centerY2));
        if ((float) centerDistance < radius1 + radius2) {
            return true;
        }
        return false;
    }

    public static boolean isCollisionOccuredCircleToPoint(float centerX1, float centerY1, float radius1, float posX, float posY) {
        double centerDistance = Math.sqrt((centerX1 - posX) * (centerX1 - posX) + (centerY1 - posY) * (centerY1 - posY));
        if ((float) centerDistance < radius1) {
            return true;
        }
        return false;
    }

}
