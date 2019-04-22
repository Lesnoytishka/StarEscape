package ru.lesnoytishka.game.sprites.Ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.base.BaseShip;
import ru.lesnoytishka.game.environment.Rect;
import ru.lesnoytishka.game.pools.BulletPool;
import ru.lesnoytishka.game.sprites.weapon.Bullet;

public class HeroShip extends BaseShip {

    private int hp = 50;
    private float speedSheep = 0.4f;

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

    public HeroShip(TextureAtlas atlas, BulletPool bullets) {
        super(atlas,"heroShip", bullets);

        setMovedSpeed();

        speedToMoveWithDelta = new Vector2();
        distanceToTouch = new Vector2();
        touch = new Vector2();
    }

    @Override
    protected void setWeapon(TextureAtlas atlas, BulletPool bullets) {
        bulletPool = bullets;
        bulletRegion = atlas.findRegion("beams1");
        bulletSpeed = new Vector2(0, 0.5f);
        reloadInterval = 0.2f;
    }

    private void setMovedSpeed() {
        speedToMove = new Vector2(speedSheep, speedSheep);
        speedToGoUp = new Vector2(0, speedSheep);
        speedToGoDown = new Vector2(0, -speedSheep);
        speedToGoLeft = new Vector2(-speedSheep, 0);
        speedToGoRight = new Vector2(speedSheep, 0);
    }

    @Override
    public void update(float delta) {
        checkingLive();
        shooting(delta);
        move(delta);
        returnToWorldArea();
    }

    @Override
    protected void shooting(float delta) {
        bulletPosition = new Vector2(position.x, position.y + halfHeight);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
    }

    private void returnToWorldArea() {
        if (getTop() > worldBounds.getTop()) {
            setTop(worldBounds.getTop());
        }
        if (getBottom() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom());
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(worldBounds.getHeight() * 0.08f);
    }

//    ----------------------------------------------------------------------------------------------

    private void shoot() {
        Bullet bullet = (Bullet) bulletPool.obtain();
        bullet.set(this, 15, bulletSpeed, bulletRegion, 0.05f, bulletPosition, worldBounds);
        soundShot.play(0.03f);
    }

    @Override
    protected void move(float delta) {
        if (isTouchOrder) {
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
        }
        if (isMoveDown) {
            position.mulAdd(speedToGoDown, delta);
        }
        if (isMoveRight) {
            position.mulAdd(speedToGoRight, delta);
        }
        if (isMoveLeft) {
            position.mulAdd(speedToGoLeft, delta);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        touchedMove(touch);
        disableButtonsMove();
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        touchedMove(touch);
        disableButtonsMove();
        return false;
    }

    private void touchedMove(Vector2 touch) {
        this.touch = touch;
        speedToMove.set(touch.cpy().sub(position)).setLength(speedSheep);
        isTouchOrder = true;
    }

    private void disableButtonsMove() {
        isMoveUp = false;
        isMoveDown = false;
        isMoveLeft = false;
        isMoveRight = false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                isMoveUp = true;
                isTouchOrder = false;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                isMoveDown = true;
                isTouchOrder = false;
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                isMoveLeft = true;
                isTouchOrder = false;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                isMoveRight = true;
                isTouchOrder = false;
                break;
            case Input.Keys.SPACE:
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                isMoveUp = false;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                isMoveDown = false;
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                isMoveLeft = false;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                isMoveRight = false;
                break;
        }
        return false;
    }
}