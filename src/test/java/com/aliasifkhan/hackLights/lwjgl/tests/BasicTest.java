package com.aliasifkhan.hackLights.lwjgl.tests;

import com.aliasifkhan.hackLights.HackLight;
import com.aliasifkhan.hackLights.HackLightEngine;
import com.aliasifkhan.hackLights.lwjgl.LibgdxLwjglUnitTest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.jupiter.api.Test;

public class BasicTest extends LibgdxLwjglUnitTest {
    private SpriteBatch batch;
    private Texture image;
    public Viewport gameViewport;

    private HackLightEngine engine;
    private HackLight light;

    @Override
    public void create() {
        gameViewport = new ExtendViewport(600, 400);
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        TextureRegion smallerLightRegion = new TextureRegion(new Texture("light1.png"));
        TextureRegion fogLightRegion = new TextureRegion(new Texture("lights.png"), 128 * 2, 128, 128, 128);

        engine = new HackLightEngine();
        engine.addLight(new HackLight(0, 0, smallerLightRegion, 0, 0.25f, 0.5f, 1, 5f));
        engine.addLight(light = new HackLight(0, 0, fogLightRegion, 1, 1, 1, 1, 2f));
    }

    @Test
    public void pirate() {
    }

    @Override
    public void render() {
        light.setPosition(Gdx.input.getX(), Gdx.input.getY());
        gameViewport.unproject(light.getPosition());

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        gameViewport.apply();
        batch.setProjectionMatrix(gameViewport.getCamera().combined);

        batch.begin();
        batch.draw(image, 0 - image.getWidth() / 2f, 0 - image.getHeight()  / 2f);
        batch.end();

        engine.render(gameViewport, batch);
    }

    @Override
    public void resize(int width, int height) {
        if (width == 0 || height == 0)
            return;

        gameViewport.update(width, height);
        engine.update(gameViewport);
    }
}
