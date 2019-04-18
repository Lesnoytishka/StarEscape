package ru.lesnoytishka.game.Sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.Base.BackgroundsObject;
import ru.lesnoytishka.game.Enviroment.Rect;
import ru.lesnoytishka.game.Enviroment.Rnd;

public class BackgroundStars extends BackgroundsObject {

    private int stepScale;
    private boolean directionScaleUp = true;

    public BackgroundStars(TextureAtlas atlas, String starName) {
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
        stepScale++;
        if (stepScale < 50) {
            if (directionScaleUp) {
                scale += 0.01f;
            } else {
                scale -= 0.01f;
            }
        } else {
            directionScaleUp = !directionScaleUp;
            stepScale = 0;
        }

        position.mulAdd(speed, delta);
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());

    }



}
