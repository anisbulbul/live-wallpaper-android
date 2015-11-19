// (c) anrosoft.com

package com.anrosoft.compass.wallpaper;

import com.badlogic.gdx.Game;

public class Wallpaper3D extends Game {

    public Android3DWallpaper service;

    Wallpaper3D(Android3DWallpaper serviceTemp){
        this.service = serviceTemp;
    }

    @Override
    public void create() {
        WallpaperAssetsTore.initAssets();
        setScreen(new WallpaperView(this));
    }

}