package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.ScaledTouchUpButton;
import ru.lesnoytishka.game.environment.Rect;

public class GameOver extends ScaledTouchUpButton {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("GAME OVER"));
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(worldBounds.getTop() - (worldBounds.halfHeight / 2.2f));
        scale = 1.2f;
    }

    @Override
    protected void action() {

    }
}
