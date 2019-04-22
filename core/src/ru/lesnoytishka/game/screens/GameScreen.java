package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.BaseScreen;
import ru.lesnoytishka.game.Enviroment.Rect;
import ru.lesnoytishka.game.Sprites.GameScreen.BackgroundClouds;
import ru.lesnoytishka.game.Sprites.GameScreen.BackgroundGameScreen;
import ru.lesnoytishka.game.Sprites.GameScreen.BackgroundStars;
import ru.lesnoytishka.game.Sprites.HeroFirstShip;

public class GameScreen extends BaseScreen {

    private TextureAtlas atlas;
    private Texture bg;
    private BackgroundGameScreen background;

    private BackgroundStars[] starsBlue;
    private BackgroundStars[] starsYellow;
    private BackgroundStars[] starsWhite;
    private BackgroundStars[] starsZero;
    private BackgroundClouds[] bgClouds;

    private Texture ship;
    private HeroFirstShip heroShip;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("Textures/bgAtlas.pack");
        bg = new Texture("Textures/gameBackground.png");
        background = new BackgroundGameScreen(new TextureRegion(bg));

        createStarsAndClouds();

        ship = new Texture("Textures/heroShip.png");
        heroShip = new HeroFirstShip(new TextureRegion(ship));
    }

    private void createStarsAndClouds() {
        starsBlue = new BackgroundStars[200];
        starsYellow = new BackgroundStars[200];
        starsWhite = new BackgroundStars[200];
        starsZero = new BackgroundStars[200];

        for (int i = 0; i < starsBlue.length; i++) {
            starsBlue[i] = new BackgroundStars(atlas, "bgStarBlue");
            starsYellow[i] = new BackgroundStars(atlas, "bgStarYellow");
            starsWhite[i] = new BackgroundStars(atlas, "bgStarWhite");
            starsZero[i] = new BackgroundStars(atlas, "bgStarZero");
        }

        bgClouds = new BackgroundClouds[9];
        for (int i = 0; i < bgClouds.length; i++) {
            bgClouds[i] = new BackgroundClouds(atlas, "bgCloud");
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (int i = 0; i < starsBlue.length; i++) {
            starsBlue[i].update(delta);
            starsYellow[i].update(delta);
            starsWhite[i].update(delta);
            starsZero[i].update(delta);
        }
        for (BackgroundClouds clouds : bgClouds) {
            clouds.update(delta);
        }
        heroShip.update(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < starsBlue.length; i++) {
            starsBlue[i].draw(batch);
            starsYellow[i].draw(batch);
            starsWhite[i].draw(batch);
            starsZero[i].draw(batch);
        }
        heroShip.draw(batch);
        for (BackgroundClouds clouds : bgClouds) {
            clouds.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < starsBlue.length; i++) {
            starsBlue[i].resize(worldBounds);
            starsYellow[i].resize(worldBounds);
            starsWhite[i].resize(worldBounds);
            starsZero[i].resize(worldBounds);
        }
        for (BackgroundClouds clouds : bgClouds) {
            clouds.resize(worldBounds);
        }
        heroShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        ship.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        heroShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        heroShip.touchDragged(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        heroShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        heroShip.keyUp(keycode);
        return super.keyUp(keycode);
    }
}
