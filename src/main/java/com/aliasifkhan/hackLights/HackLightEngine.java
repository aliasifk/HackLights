package com.aliasifkhan.hackLights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

public class HackLightEngine {
    private final Array<HackLight> lights = new Array<>();

    private final Color ambientLightColor;

    private FrameBuffer lightsBuffer;
    private final TextureRegion lightsBufferRegion;

    private final SpriteBatch batch;

    public HackLightEngine() {
        this(0.1f, 0.1f, 0.1f, 1f);
    }

    public HackLightEngine(float ambientR, float ambientG, float ambientB, float ambientA) {
        this.ambientLightColor = new Color(ambientR, ambientG, ambientB, ambientA);

        lightsBufferRegion = new TextureRegion();

        batch = new SpriteBatch();
    }

    public void draw(Matrix4 projectionMatrix) {
        batch.setProjectionMatrix(projectionMatrix);

        lightsBuffer.begin();
        Gdx.gl.glClearColor(ambientLightColor.r, ambientLightColor.g, ambientLightColor.b, ambientLightColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        Gdx.gl20.glEnable(GL20.GL_BLEND);

        batch.begin();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, -1);

        for (HackLight light : lights)
            light.draw(batch);

        batch.end();
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        lightsBuffer.end();

        batch.getProjectionMatrix().setToOrtho2D(0, lightsBuffer.getHeight(), lightsBuffer.getWidth(), lightsBuffer.getHeight());

        batch.begin();
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.draw(lightsBufferRegion, 0, lightsBuffer.getHeight());
        batch.end();
    }


    public void update(int width, int height) {
        if (lightsBuffer != null)
            lightsBuffer.dispose();

        lightsBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        lightsBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        lightsBufferRegion.setRegion(lightsBuffer.getColorBufferTexture());
        lightsBufferRegion.flip(false, true);
    }

    public void addLight(HackLight light) {
        lights.add(light);
    }

    public HackLight getLight(int index) {
        return lights.get(index);
    }

    public void removeLight(int index) {
        lights.removeIndex(index);
    }

    public void removeLight(HackLight light) {
        lights.removeValue(light, true);
    }

    public Color getAmbientLightColor() {
        return ambientLightColor;
    }

    public void setAmbientLightColor(Color color) {
        ambientLightColor.set(color);
    }

    public int size(){
        return lights.size;
    }

    public void setAmbientLightColor(float r, float g, float b, float a) {
        ambientLightColor.set(r, g, b, a);
    }
}
