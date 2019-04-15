package ru.lesnoytishka.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.Sprite;
import ru.lesnoytishka.game.Enviroment.Rect;

public class BackgroundMainMenu extends Sprite {

    public BackgroundMainMenu(TextureRegion region) {
        super(region);
    }

    public BackgroundMainMenu(Texture texture, int count, int width, int height) {
        super(texture, count, width, height);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        position.set(worldBounds.position);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}