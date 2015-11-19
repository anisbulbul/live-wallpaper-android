// (c) anrosoft.com

package com.anrosoft.game.wallpaper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;
import com.badlogic.gdx.math.Matrix4;

public class Android3DWallpaper extends AndroidLiveWallpaperService implements SensorEventListener{
    public Matrix4 rotationPixelMatrix = new Matrix4();
    public float azimut = 0;

    public float[] geomagnetic = new float[3];
    public float[] gravity = new float[3];

    @Override
    public void onCreateApplication () {
        super.onCreateApplication();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = true;
        config.useWakelock = false;
        config.useAccelerometer = true;
        config.getTouchEventsForLiveWallpaper = true;

        SensorManager sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        ApplicationListener listener = new Wallpaper3D(this);
        initialize(listener, config);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationPixelMatrix.val, event.values);

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = orientation[0];
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = lowPassFilter(event.values.clone(), geomagnetic);
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = lowPassFilter(event.values.clone(), gravity);
        }
    }

    static final float ALPHA = 0.25f;

    /**
     * @see <a href="http://en.wikipedia.org/wiki/Low-pass_filter#Algorithmic_implementation"></a>
     * @see <a href="http://developer.android.com/reference/android/hardware/SensorEvent.html#values"></a>
     * @see <a href="http://blog.thomnichols.org/2011/08/smoothing-sensor-data-with-a-low-pass-filter"></a>
     */
    protected float[] lowPassFilter(float[] input, float[] output) {
        if ( output == null ) return input;

        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // shut up
    }

    public static class MyLiveWallpaperListener extends WallpaperView implements AndroidWallpaperListener {

        public MyLiveWallpaperListener(Wallpaper3D game) {
            super(game);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void offsetChange (float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            Log.i("LiveWallpaper test", "offsetChange(xOffset:"+xOffset+" yOffset:"+yOffset+" xOffsetSteep:"+xOffsetStep+" yOffsetStep:"+yOffsetStep+" xPixelOffset:"+xPixelOffset+" yPixelOffset:"+yPixelOffset+")");
        }

        @Override
        public void previewStateChange (boolean isPreview) {
            Log.i("LiveWallpaper test", "previewStateChange(isPreview:"+isPreview+")");
        }
    }


}

