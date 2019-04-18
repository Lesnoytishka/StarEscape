package ru.lesnoytishka.game.Base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledTouchUpButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean pressed;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }

    protected abstract void action();

    @Override
    public boolean touchDown (Vector2 touch,int pointer) {
        if (pressed || !isInside(touch)) {
            return false;
        }
        scale = PRESS_SCALE;
        this.pointer = pointer;
        pressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer != this.pointer || !pressed) {
            return false;
        }
        if (isInside(touch)){
            action();
        }
        scale = 1f;
        pressed = false;
        return false;
    }
}
