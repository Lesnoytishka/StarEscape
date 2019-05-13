package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.ScaledTouchUpButton;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.screens.GameScreen;

public class NewGame extends ScaledTouchUpButton {

    private GameScreen screen;

    public NewGame(TextureAtlas atlas, GameScreen screen) {
        super(atlas.findRegion("NEW GAME"));
        this.screen = screen;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(worldBounds.getTop() - worldBounds.halfHeight);
        scale = 1f;
    }

    @Override
    protected void action() {
        screen.reset();
    }
}
