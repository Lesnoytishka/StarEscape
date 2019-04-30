package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.ScaledTouchUpButton;
import ru.lesnoytishka.game.environment.Rect;
import ru.lesnoytishka.game.screens.GameScreen;

public class NewGame extends ScaledTouchUpButton {

    private Game game;

    public NewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("NEW GAME"));
        this.game = game;
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
        game.setScreen(new GameScreen(game));
    }
}
