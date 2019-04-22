package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.BaseScreen;
import ru.lesnoytishka.game.Enviroment.Rect;
import ru.lesnoytishka.game.Sprites.MainMenu.BackgroundMainMenu;
import ru.lesnoytishka.game.Sprites.MainMenu.ButtonExit;
import ru.lesnoytishka.game.Sprites.MainMenu.ButtonPlay;

public class MenuScreen extends BaseScreen {

    private Game game;

    private TextureAtlas atlas;
    private Texture bg;
    private BackgroundMainMenu background;

    private ButtonPlay btnPlay;
    private ButtonExit btnExit;


    public MenuScreen (Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("Textures/bgAtlas.pack");
        bg = new Texture("Textures/cosmos.png");
        background = new BackgroundMainMenu(bg, 16, 400, 500);
        btnPlay = new ButtonPlay(atlas, game);
        btnExit = new ButtonExit(atlas, game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
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
