package ru.lesnoytishka.game.Sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.lesnoytishka.game.Base.Sprite;
import ru.lesnoytishka.game.Enviroment.Rect;

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
