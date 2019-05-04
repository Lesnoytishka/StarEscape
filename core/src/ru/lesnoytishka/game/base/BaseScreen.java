package ru.lesnoytishka.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.utils.MatrixUtils;
import ru.lesnoytishka.game.utils.Rect;

public abstract class BaseScreen implements Screen, InputProcessor {

    protected enum PlayingStatus {PLAYING, PASUSE, GAME_OVER}

    protected PlayingStatus playingStatus = PlayingStatus.PLAYING;

    protected SpriteBatch batch;
    protected Rect worldBounds;
    private Rect screenBounds;
    private Rect glBounds;
    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;
    private Vector2 touch;

    @Override
    public void show() {
        System.out.println("create");
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        worldBounds = new Rect();
        screenBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds){

    }

    @Override
    public void pause() {
        playingStatus = PlayingStatus.PASUSE;
    }

    @Override
    public void resume() {
        playingStatus = PlayingStatus.PLAYING;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("Dispose");
        batch.dispose();
    }

    public PlayingStatus isPause() {
        return playingStatus;
    }

    //    -----------------------------------------------------------------------------
//    Input methods


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer){
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer){
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
