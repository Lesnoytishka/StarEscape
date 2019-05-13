package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.utils.Rect;

public class BackgroundGameScreen extends Sprite {

    public BackgroundGameScreen(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        position.set(worldBounds.position);
    }
}
