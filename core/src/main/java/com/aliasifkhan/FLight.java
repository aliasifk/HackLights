package com.aliasifkhan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
//import com.spark.utils.Globals;

public class FLight {

    public static final byte
            mBasic = 1,
            mSmall = 2,
            mBigBasic = 3;

    //Light Pool since there can be many lights
    public static FLight[] lightsList = new FLight[512];

    public static Color ambientLight = new Color();
    public Vector2 mPosition;
    TextureRegion reg;
    float distance;
    float r;
    float g;
    float b;
    float a;
    boolean deleted;
    boolean isVisible;
    byte mType;
    boolean isShaky;
    float offsetX, offsetY;
    //

    public final static void initLights() {
        // this is most important, this is the ambient light and is what gives u brightness/darkness of bg
        ambientLight.set(0.1f, 0.1f, 0.1f, 1f);
        for (int i = 0; i < lightsList.length; i++) {
            lightsList[i] = new FLight();
            lightsList[i].setDeleted(true);
            lightsList[i].mPosition = new Vector2();
            lightsList[i].isVisible = true;

        }

    }

    public final static void clearLights() {
        for (int i = 0; i < lightsList.length; i++) {
            lightsList[i].setDeleted(false);
        }
    }

    public static void renderLights(SpriteBatch batch) {


        for (int i = 0; i < lightsList.length; i++) {
            //Culling is done here
//            if (lightsList[i].mPosition.x > myScene.mPlayer.mPosition.x - Globals.RENDER_PROXIMITY && lightsList[i].mPosition.x < myScene.mPlayer.mPosition.x + Globals.RENDER_PROXIMITY
//                    && lightsList[i].mPosition.y < myScene.mPlayer.mPosition.y + Globals.RENDER_PROXIMITY && lightsList[i].mPosition.y > myScene.mPlayer.mPosition.y - Globals.RENDER_PROXIMITY) {
//                lightsList[i].isVisible = true;
//            } else {
//                lightsList[i].isVisible = false;
//            }

            if (FLight.lightsList[i].isDeleted() || !lightsList[i].isVisible) {
                continue;
            }
            batch.setColor(lightsList[i].getColor());
            lightsList[i].render(batch);

        }

        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

    }

    public final static FLight addLight(Vector2 mPosition, byte mType, float distance, int r, int g, int b, int a, float offsetX, float offsetY, boolean isShaky) {

        int i = 0;
        // FakeLight
        while (i < lightsList.length && !lightsList[i].isDeleted()) i++;
        if (i < lightsList.length) {
            /*     lightsList[i].setLightType(myType);*/

            lightsList[i].setmPosition(mPosition);
            lightsList[i].setOffsets(offsetX, offsetY);
            lightsList[i].setColor(r / 255f, g / 255f, b / 255f, a / 255f);
            lightsList[i].setDistance(distance);
            lightsList[i].setDeleted(false);
            lightsList[i].mType = mType;
            lightsList[i].isShaky = isShaky;
            return lightsList[i];
        }

        return null;

    }


    public static void killAll() {
        for (int i = 0; i < lightsList.length; i++)
            lightsList[i].deleted = true;

    }

    public static FLight getLight(int index) {
        return lightsList[index];
    }

    public static void remove(int lightIndex) {
        if(lightIndex < 0 || lightIndex >= lightsList.length){
            return;
        }
        lightsList[lightIndex].deleted = true;
    }

    static   TextureRegion  light1 = new TextureRegion(new Texture("light1.png"));

    public void render(SpriteBatch batch) {
        switch (mType) {
            case mBasic:
                reg = light1;
                batch.draw(reg.getTexture(),
                        mPosition.x - ((reg.getRegionWidth() * distance) / 2.0f), mPosition.y - (reg.getRegionHeight() * distance) / 2.0f,
                        reg.getRegionWidth() * distance, reg.getRegionHeight() * distance,
                        reg.getRegionX(), reg.getRegionY(),
                        reg.getRegionWidth(), reg.getRegionHeight(),
                        false, true);
                break;
        }
    }

    private void setmPosition(Vector2 mPosition) {
        this.mPosition = mPosition;
    }

    private void setOffsets(float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    private void setDistance(float distance) {
        this.distance = distance;
    }

    private boolean isDeleted() {
        return deleted;
    }

    private void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Color getColor() {
        return new Color(r, g, b, a);
    }

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

    }

}
