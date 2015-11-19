package com.anrosoft.compass.wallpaper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by anisbulbul on 7/24/2015.
 */
public class WallpaperAssetsTore {

    public static final float width = Gdx.graphics.getWidth();
    public static final float height = Gdx.graphics.getHeight();
    public static final float CYLINDER = 4.5f;
    public static final int NUMBER_OF_EARTH = 6;

    public static Environment environment;
    public static ModelBuilder modelBuilder;
    public static Viewport viewPort;

    public static Texture[] earthTexture;
    public static Model earthModel;
    public static ModelInstance[] earthModelInstance;
    public static ModelBatch earthModelBatch;

    public static Texture[] wheelTexture;
    public static Model wheelModel;
    public static ModelInstance[] wheelModelInstance;
    public static ModelBatch wheelModelBatch;

    public static Texture centerTexture;
    public static Model centerModel;
    public static ModelInstance centerModelInstance;
    public static ModelBatch centerModelBatch;

    public static int earthIndex = 1;
    public static int wheelIndex = 1;

    public static void initAssets() {
        initTexture();
        setupModelBuilder();
        setupEnvironment();  // Environment setup
        setupEarth();    // Sphere setup
        setupCenter();   // Vernier setup
        setupWheel();    // Bearing setup
        enableFaceCalling();  // Enable face culling
    }

    public static void initTexture() {

    }

    public static void setupModelBuilder() {
        modelBuilder = new ModelBuilder();
    }

    public static void enableFaceCalling() {
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl.glCullFace(GL20.GL_BACK);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
    }

    public static void setupEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .3f, .3f, .3f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.0f, -0.0f, 10.0f));
    }

    public static void setupWheel() {

        wheelModelInstance = new ModelInstance[NUMBER_OF_EARTH];
        wheelTexture = new Texture[NUMBER_OF_EARTH];
        for (int i = 0; i < NUMBER_OF_EARTH; i++) {
            wheelTexture[i] = new Texture("wheel" + (i + 1) + ".png");
            wheelTexture[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            wheelModelBatch = new ModelBatch();
            wheelModel = modelBuilder.createCylinder(CYLINDER, 0.64f, CYLINDER, 32,
                    new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
            wheelModelInstance[i] = new ModelInstance(wheelModel);
            wheelModelInstance[i].materials.first().set(TextureAttribute.createDiffuse(wheelTexture[i]));
            wheelModelInstance[i].materials.first().set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

        }


    }


    public static void setupCenter() {
        centerTexture = new Texture("center.png");
        centerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        centerModelBatch = new ModelBatch();
        centerModel = modelBuilder.createBox(4.0f, 4.0f, 1.0f,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        centerModelInstance = new ModelInstance(centerModel);
        centerModelInstance.materials.first().set(TextureAttribute.createDiffuse(centerTexture));
        centerModelInstance.materials.first().set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
    }


    public static void setupEarth() {
        earthModelInstance = new ModelInstance[NUMBER_OF_EARTH];
        earthTexture = new Texture[NUMBER_OF_EARTH];
        for (int i = 0; i < NUMBER_OF_EARTH; i++) {
            earthTexture[i] = new Texture("earth" + (i + 1) + ".png");
            earthTexture[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            earthModel = modelBuilder.createSphere(7.0f, 7.0f, 7.0f, 64, 64,
                    new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
            earthModelBatch = new ModelBatch();
            earthModelInstance[i] = new ModelInstance(earthModel);
            earthModelInstance[i].materials.first().set(TextureAttribute.createDiffuse(earthTexture[i]));
        }
    }
}
