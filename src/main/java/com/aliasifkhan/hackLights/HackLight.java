package com.aliasifkhan.hackLights;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HackLight extends Sprite {
    private boolean visible = true;

    public HackLight(TextureRegion region, float r, float g, float b, float a) {
        this(region, r, g, b, a, 1);
    }

    public HackLight(TextureRegion region, float r, float g, float b, float a, float scale) {
        super(region);

        setOriginCenter();
        setColor(r, g, b, a);
        setScale(scale);
    }

    @Override
    public void draw(Batch batch) {
        if (!visible)
            return;

        super.draw(batch);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
