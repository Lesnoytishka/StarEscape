package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.lesnoytishka.game.base.BackgroundsObject;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.utils.Rnd;

public class BackgroundClouds extends BackgroundsObject {

    private int stepScale;
    private boolean directionScaleUp = true;

    public BackgroundClouds(TextureAtlas atlas, String starName) {
        super(atlas, starName, -0.005f, 0.005f, -0.05f, -0.25f, 0.1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        this.worldBounds = worldBounds;
        float posX = Rnd.getFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.getFloat(worldBounds.getTop(), worldBounds.getBottom());
        this.position.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stepScale++;
        if (stepScale < 80) {
            if (directionScaleUp) {
                scale += 0.001f;
            } else {
                scale -= 0.001f;
            }
        } else {
            directionScaleUp = !directionScaleUp;
            stepScale = 0;
        }

        position.mulAdd(speed, delta);
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
            angle = Rnd.getFloat(0, 180);
        }
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
            angle = Rnd.getFloat(0, 180);
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
            angle = Rnd.getFloat(0, 180);
        }
        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getBottom());
            angle = Rnd.getFloat(0, 180);
        }

    }
}
