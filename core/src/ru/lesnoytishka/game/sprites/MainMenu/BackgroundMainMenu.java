package ru.lesnoytishka.game.sprites.MainMenu;

import com.badlogic.gdx.graphics.Texture;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.utils.Rect;

public class BackgroundMainMenu extends Sprite {

    public BackgroundMainMenu(Texture texture, int count, int width, int height) {
        super(texture, width, height, count);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        position.set(worldBounds.position);
    }
}
