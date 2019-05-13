package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.utils.Rect;

public class GameOver extends Sprite {

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
}
