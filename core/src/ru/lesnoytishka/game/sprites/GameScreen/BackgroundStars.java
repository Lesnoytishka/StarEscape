package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.BackgroundsObject;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.utils.Rnd;

public class BackgroundStars extends BackgroundsObject {

    BackgroundStars(TextureAtlas atlas, String starName) {
        super(atlas, starName);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.01f);
        this.worldBounds = worldBounds;
        float posX = Rnd.getFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.getFloat(worldBounds.getTop(), worldBounds.getBottom());
        this.position.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        checkBounds();
    }



}
