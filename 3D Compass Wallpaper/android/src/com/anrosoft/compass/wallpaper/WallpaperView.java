// (c) anrosoft.com

package com.anrosoft.compass.wallpaper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class WallpaperView extends WallpaperAssetsTore implements Screen {

    Wallpaper3D game;
    PerspectiveCamera mainCamera, wheelCamera;

    public WallpaperView(final Wallpaper3D game) {
        this.game = game;
        setupCamera();  // Camera setup
    }


    private void setupCamera() {
        mainCamera = new PerspectiveCamera(50, width, height);
        mainCamera.position.set(0f, 0f, 15f);
        mainCamera.lookAt(0, 0, 0);
        mainCamera.near = -1f;
        mainCamera.far = 300f;
        mainCamera.update();

        wheelCamera = new PerspectiveCamera(30f, width, height);
        wheelCamera.position.set(0f, CYLINDER, 27.5f);
        wheelCamera.lookAt(0, 0, 0);
        wheelCamera.near = -1f;
        wheelCamera.far = 300f;
        wheelCamera.update();

        viewPort = new FillViewport(width, height, mainCamera);
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

        drawEarthModel();
        drawCenterModel();
        drawWheelModel();

    }

    private void drawWheelModel() {
        wheelModelBatch.begin(wheelCamera);
        Matrix4 matrix = new Matrix4();
        matrix.rotate(0, 1, 0, -(game.service.azimut * 360.0f) / (2 * (float) Math.PI));
        matrix.trn(0, CYLINDER, 0);
        wheelModelInstance[wheelIndex].transform.set(matrix);
        wheelModelInstance[wheelIndex].transform.rotate(0, 1, 0, 225);
        wheelModelBatch.render(wheelModelInstance[wheelIndex]);
        wheelModelBatch.end();
    }

    private void drawCenterModel() {
        centerModelBatch.begin(mainCamera);
        centerModelInstance.transform.setToRotation(0, 0, 1, 90);
        centerModelBatch.render(centerModelInstance);
        centerModelBatch.end();
    }

    private void drawEarthModel() {
        earthModelBatch.begin(mainCamera);
        earthModelInstance[earthIndex].transform.set(game.service.rotationPixelMatrix);
        earthModelInstance[earthIndex].transform.rotate(1, 0, 0, -90f);
        earthModelInstance[earthIndex].transform.scale(1, -1, 1);
        earthModelInstance[earthIndex].transform.rotate(0, 1, 0, 225);
        earthModelBatch.render(earthModelInstance[earthIndex], environment);
        earthModelBatch.end();
    }

    private void updateScreen(float delta) {
        if (Gdx.input.justTouched()) {
//            setupEarth(earthIndexTemp);
//            setupCamera();
//            earthIndexTemp++;
//            if (earthIndexTemp == 7) {
//                earthIndexTemp = 1;
//            }
        }
    }

    @Override
    public void render(float delta) {
        updateScreen(delta);
        drawScreen(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
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