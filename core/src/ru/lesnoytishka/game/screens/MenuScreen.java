package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.BaseScreen;
import ru.lesnoytishka.game.Enviroment.Rect;
import ru.lesnoytishka.game.Sprites.BackgroundMainMenu;
import ru.lesnoytishka.game.Sprites.HeroFirstShip;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture ship;
    private BackgroundMainMenu background;
    private HeroFirstShip heroShip;

    @Override
    public void show() {
        super.show();
        bg = new Texture("cosmos.png");
        background = new BackgroundMainMenu(bg, 16, 400, 500);
        ship = new Texture("heroShip.png");
        heroShip = new HeroFirstShip(new TextureRegion(ship));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        heroShip.update(delta);
        batch.begin();
        background.drawAnimated(batch);
        heroShip.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        heroShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        ship.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        heroShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        heroShip.touchDragged(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == 51) {
            System.out.println(heroShip.toString());
        }
        return false;
    }
}
