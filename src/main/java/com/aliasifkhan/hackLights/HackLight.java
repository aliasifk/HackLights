package com.aliasifkhan.hackLights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class HackLight {
    private final Color color;

    private final Vector2 position;

    private TextureRegion region;
    private float distance;

    private boolean visible = true;

    public HackLight(float x, float y, TextureRegion region, float r, float g, float b, float a, float distance) {
        this.position = new Vector2(x, y);
        this.region = region;
        this.distance = distance;
        this.color = new Color(r, g, b, a);
    }

    public void render(SpriteBatch batch) {
        if (!visible)
            return;

        batch.setColor(color);

        batch.draw(region.getTexture(),
                position.x - ((region.getRegionWidth() * distance) / 2.0f), position.y - (region.getRegionHeight() * distance) / 2.0f,
                region.getRegionWidth() * distance, region.getRegionHeight() * distance,
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight(),
                false, true);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }
}
