package com.aliasifkhan.hackLights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HackLightEngine {
    private final Array<HackLight> lights = new Array<>();

    private final Color ambientLightColor;

    private FrameBuffer lightsBuffer;

    public HackLightEngine() {
        this(0.1f, 0.1f, 0.1f, 1f);
    }

    public HackLightEngine(float ambientR, float ambientG, float ambientB, float ambientA) {
        this.ambientLightColor = new Color(ambientR, ambientG, ambientB, ambientA);
    }

    public void render(SpriteBatch batch) {
        Color oldColor = batch.getColor();

        lightsBuffer.begin();
        Gdx.gl.glClearColor(ambientLightColor.r, ambientLightColor.g, ambientLightColor.b, ambientLightColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        Gdx.gl20.glEnable(GL20.GL_BLEND);

        batch.begin();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, -1);

        for (HackLight light : lights)
            light.render(batch);

        batch.end();
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        lightsBuffer.end();

        batch.getProjectionMatrix().setToOrtho2D(0, lightsBuffer.getHeight(), lightsBuffer.getWidth(), lightsBuffer.getHeight());

        batch.begin();
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.draw(lightsBuffer.getColorBufferTexture(), 0, lightsBuffer.getHeight(), 0, 0, lightsBuffer.getWidth(), lightsBuffer.getHeight(), 1, 1, 0, 0, 0, lightsBuffer.getWidth(), lightsBuffer.getHeight(), false, true);
        batch.end();

        batch.setColor(oldColor);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void update(Viewport viewport) {
        update(viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    public void update(float worldWidth, float worldHeight) {
        if (lightsBuffer != null)
            lightsBuffer.dispose();

        lightsBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Math.round(worldWidth), Math.round(worldHeight), false);
        lightsBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
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

    public void setAmbientLightColor(float r, float g, float b, float a) {
        ambientLightColor.set(r, g, b, a);
    }
}
