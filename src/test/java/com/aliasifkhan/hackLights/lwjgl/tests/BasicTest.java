package com.aliasifkhan.hackLights.lwjgl.tests;

import com.aliasifkhan.hackLights.HackLight;
import com.aliasifkhan.hackLights.HackLightEngine;
import com.aliasifkhan.hackLights.lwjgl.LibgdxLwjglUnitTest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.jupiter.api.Test;

public class BasicTest extends LibgdxLwjglUnitTest {
    private SpriteBatch batch;
    private Texture image;
    public Viewport gameViewport;

    private HackLightEngine engine;
    private HackLight fogLight, libgdxLight;

    @Override
    public void create() {
        gameViewport = new ExtendViewport(600, 400);
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        TextureRegion smallerLightRegion = new TextureRegion(new Texture("light1.png"));
        TextureRegion fogLightRegion = new TextureRegion(new Texture("lights.png"), 128 * 2, 128, 128, 128);

        engine = new HackLightEngine();
        engine.addLight(libgdxLight = new HackLight(smallerLightRegion, 0, 0.25f, 0.5f, 1, 5f));
        libgdxLight.setOriginBasedPosition(0, 0);
        engine.addLight(this.fogLight = new HackLight(fogLightRegion, 1, 1, 1, 1, 2f));
    }

    @Test
    public void pirate() {
    }

    @Override
    public void render() {
        Vector2 vec = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        gameViewport.unproject(vec);
        fogLight.setOriginBasedPosition(vec.x, vec.y);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        gameViewport.getCamera().position.set(
                gameViewport.getCamera().position.x + 10 * Gdx.graphics.getDeltaTime(),
                gameViewport.getCamera().position.y + 10 * Gdx.graphics.getDeltaTime(),
                gameViewport.getCamera().position.z
        );

        gameViewport.apply();
        batch.setProjectionMatrix(gameViewport.getCamera().combined);

        batch.begin();
        batch.draw(image, 0 - image.getWidth() / 2f, 0 - image.getHeight()  / 2f);
        batch.end();

        engine.render(batch);
    }

    @Override
    public void resize(int width, int height) {
        if (width == 0 || height == 0)
            return;

        gameViewport.update(width, height);
        engine.update(width, height);
    }
}
