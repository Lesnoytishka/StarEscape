package ru.lesnoytishka.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarEscape extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private TextureRegion[] region = new TextureRegion[16];
	private Animation backgroundAnimation;
	private TextureRegion background;

	private float stateTime;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("CosmoAnim.png");
		for (int i = 0; i < 16; i++) {
			region[i] = new TextureRegion(img, (i * 800), 0, 268, 500);
		}
		backgroundAnimation = new Animation(0.033f, region);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.5f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += Gdx.graphics.getDeltaTime();
		background = (TextureRegion) backgroundAnimation.getKeyFrame(stateTime, true);

		batch.begin();
		batch.draw(background,
				0,
				0,
				Gdx.app.getGraphics().getWidth(),
				Gdx.app.getGraphics().getHeight()
		);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
