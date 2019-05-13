package ru.lesnoytishka.game.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.utils.Rnd;

public abstract class BackgroundsObject extends Sprite {

    protected Vector2 speed;
    protected Rect worldBounds;
    private int stepScale;
    private boolean directionScaleUp = true;

    public BackgroundsObject(TextureAtlas atlas, String starName) {
        super(atlas.findRegion(starName));
        float vx = Rnd.getFloat(-0.005f, 0.005f);
        float vy = Rnd.getFloat(-0.5f, -0.1f);
        speed = new Vector2(vx, vy);
        setHeightProportion(0.01f);

    }

    public BackgroundsObject(TextureAtlas atlas, String starName, float speedX_min, float speedX_max,float speedY_min, float speedY_max,  float heightProportions) {
        super(atlas.findRegion(starName));
        float vx = Rnd.getFloat(speedX_min, speedX_max);
        float vy = Rnd.getFloat(speedY_min, speedY_max);
        speed = new Vector2(vx, vy);
        setHeightProportion(heightProportions);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stepScale++;
        if (stepScale < 80) {
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
    }

    protected void checkBounds(){
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
