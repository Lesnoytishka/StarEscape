package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.base.BaseScreen;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.sprites.MainMenu.BackgroundMainMenu;
import ru.lesnoytishka.game.sprites.MainMenu.ButtonExit;
import ru.lesnoytishka.game.sprites.MainMenu.ButtonPlay;

public class MenuScreen extends BaseScreen {

    private Game game;

    private TextureAtlas atlas;
    private Texture bg;
    private BackgroundMainMenu background;

    private ButtonPlay btnPlay;
    private ButtonExit btnExit;

    private Music music;

    public MenuScreen (Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/bgAtlas.pack");
        bg = new Texture("textures/cosmos.png");
        background = new BackgroundMainMenu(bg, 16, 400, 500);
        playMusic();
        btnPlay = new ButtonPlay(atlas, game);
        btnExit = new ButtonExit(atlas, game);
    }

    private void playMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/PPKreload.mp3"));
        music.setVolume(0.7f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
    }

    private void draw() {
        batch.begin();
        background.drawAnimated(batch);
        btnPlay.draw(batch);
        btnExit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        btnPlay.resize(worldBounds);
        btnExit.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        btnPlay.touchDown(touch, pointer);
        btnExit.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        btnPlay.touchUp(touch, pointer);
        btnExit.touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
