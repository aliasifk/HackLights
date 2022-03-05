package com.aliasifkhan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture image;
	private FrameBuffer lightBuffer;
	public static Viewport gameViewport;
	public static OrthographicCamera gameCamera;
	int f_WIDTH, f_HEIGHT;
	float LIGHT_SIZE = 3f;

	private FLight light;
	@Override
	public void create() {
		gameCamera = new OrthographicCamera();
		gameViewport = new ExtendViewport(600,400, gameCamera);
		gameCamera.setToOrtho(false);
		batch = new SpriteBatch();
		image = new Texture("libgdx.png");
		FLight.initLights();
		light =  FLight.addLight(new Vector2(400,200), FLight.mBasic, LIGHT_SIZE, 247, 144, 10, 255, 0f, 0f, false);
	}

	@Override
	public void render() {
		Vector3 pos2 = gameViewport.unproject(new Vector3(Gdx.input.getX() ,  Gdx.input.getY(), 1));
		light.mPosition.x = pos2.x;
		light.mPosition.y =  pos2.y;
		//
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameViewport.apply();
		batch.setProjectionMatrix(gameViewport.getCamera().combined);


		batch.begin();
		batch.draw(image, 140, 210);
		batch.end();
		renderLights(batch);
		//renderUI(batch)
	}

	private void renderLights(SpriteBatch batch) {
		/**TODO: cordinates not in complete sync?*/
		lightBuffer.begin();

		Gdx.gl.glClearColor(FLight.ambientLight.r, FLight.ambientLight.g, FLight.ambientLight.b, FLight.ambientLight.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		Gdx.gl20.glEnable(GL20.GL_BLEND);

		batch.begin();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, -1);


		FLight.renderLights(batch); // rendering all light sprites with batch.draw

		batch.end();
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		lightBuffer.end();


		Vector3 pos2 = gameViewport.unproject(new Vector3(0, gameViewport.getScreenHeight(), 1));

		batch.getProjectionMatrix().setToOrtho2D(pos2.x - 50, pos2.y - 50, f_WIDTH, f_HEIGHT);

		batch.begin();
		batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
		batch.draw(lightBuffer.getColorBufferTexture(), pos2.x - 50, pos2.y - 50, 0, 0, f_WIDTH, f_HEIGHT, 1, 1, 0, 0, 0, f_WIDTH, f_HEIGHT, false, true);
		batch.end();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


	}

	public void resize(int width, int height) {
		if(width == 0 || height == 0){
			return;
		};
		gameViewport.update(width, height);
//		uiViewport.update(width, height);
		f_WIDTH = Math.round(gameViewport.getWorldWidth() * gameCamera.zoom) + 100;
		f_HEIGHT = Math.round(gameViewport.getWorldHeight() * gameCamera.zoom) + 100;

		if (lightBuffer != null) lightBuffer.dispose();

		lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, f_WIDTH, f_HEIGHT, false);
		lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		//Display UI buffer here
//		if (UIBuffer != null) UIBuffer.dispose();
//		UIBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
//		UIBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	}

	@Override
	public void dispose() {
		batch.dispose();
		image.dispose();
	}
}