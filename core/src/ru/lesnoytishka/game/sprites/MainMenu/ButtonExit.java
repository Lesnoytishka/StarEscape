package ru.lesnoytishka.game.sprites.MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.ScaledTouchUpButton;
import ru.lesnoytishka.game.utils.Rect;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btnExit"));
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.08f);
        setRight(worldBounds.getRight() - 0.08f);
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }
}
