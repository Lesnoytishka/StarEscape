package ru.lesnoytishka.game.Sprites.MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.Base.ScaledTouchUpButton;
import ru.lesnoytishka.game.Enviroment.Rect;
import ru.lesnoytishka.game.screens.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

    private Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btnPlay"));
        this.game = game;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.08f);
        setLeft(worldBounds.getLeft() + 0.08f);
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen());
    }
}