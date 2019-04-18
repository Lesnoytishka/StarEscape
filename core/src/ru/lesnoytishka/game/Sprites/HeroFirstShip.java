package ru.lesnoytishka.game.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.Sprite;
import ru.lesnoytishka.game.Enviroment.Rect;

public class HeroFirstShip extends Sprite {

    private float speed = 0.25f;

    private boolean isTouchOrder = false;
    private boolean isMoveUp = false;
    private boolean isMoveDown = false;
    private boolean isMoveLeft = false;
    private boolean isMoveRight = false;

    private Vector2 speedToGoUp;
    private Vector2 speedToGoDown;
    private Vector2 speedToGoLeft;
    private Vector2 speedToGoRight;

    private Vector2 speedToMove;
    private Vector2 speedToMoveWithDelta;
    private Vector2 distanceToTouch;
    private Vector2 touch;

    public HeroFirstShip(TextureRegion region) {
        super(region);
        speedToMove = new Vector2(speed, speed);
        speedToGoUp = new Vector2(0, speed);
        speedToGoDown = new Vector2(0, -speed);
        speedToGoLeft = new Vector2(-speed, 0);
        speedToGoRight = new Vector2(speed, 0);

        speedToMoveWithDelta = new Vector2();
        distanceToTouch = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void update(float delta) {
        move(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.08f);
    }

    private void move(float delta){
        if (isTouchOrder){
            distanceToTouch.set(touch);
            speedToMoveWithDelta.set(speedToMove);
            speedToMoveWithDelta.scl(delta);
            if (distanceToTouch.sub(position).len() > speedToMoveWithDelta.len()) {
                position.mulAdd(speedToMove, delta);
            } else {
                position.set(touch);
            }
        }
        if (isMoveUp) {
            position.mulAdd(speedToGoUp, delta);
            angle = 0f;
        }
        if (isMoveDown) {
            position.mulAdd(speedToGoDown, delta);
            angle = 180f;
        }
        if (isMoveRight) {
            position.mulAdd(speedToGoRight, delta);
            angle = 270f;
        }
        if (isMoveLeft) {
            position.mulAdd(speedToGoLeft, delta);
            angle = 90f;
        }
        if (isMoveUp && isMoveLeft){
            angle = 45f;
        }
        if (isMoveUp && isMoveRight){
            angle = 315f;
        }
        if (isMoveDown && isMoveLeft){
            angle = 135f;
        }
        if (isMoveDown && isMoveRight){
            angle = 225f;
        }
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        speedToMove.set(touch.cpy().sub(position)).setLength(speed);
        isTouchOrder = true;
        disableButtonsMove();
        return false;
    }

    private void disableButtonsMove() {
        isMoveUp = false;
        isMoveDown = false;
        isMoveLeft = false;
        isMoveRight = false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        this.touch = touch;
        speedToMove.set(touch.cpy().sub(position)).setLength(speed);
        isTouchOrder = true;
        disableButtonsMove();
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 51:    // w
                isMoveUp = true;
                isTouchOrder = false;
                break;
            case 47:    // s
                isMoveDown = true;
                isTouchOrder = false;
                break;
            case 29:    // a
                isMoveLeft = true;
                isTouchOrder = false;
                break;
            case 32:    // d
                isMoveRight = true;
                isTouchOrder = false;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case 51:    // w
                isMoveUp = false;
                break;
            case 47:    // s
                isMoveDown = false;
                break;
            case 29:    // a
                isMoveLeft = false;
                break;
            case 32:    // d
                isMoveRight = false;
                break;
        }
        return false;
    }
}
