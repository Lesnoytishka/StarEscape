package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.BaseScreen;
import ru.lesnoytishka.game.Ships.HeroShip;

public class MenuScreen extends BaseScreen {

    private static final int COUNT_SHOT = 16;
    private static final int STEP_NEXT_SHOT_WIDTH = 400;
    private static final int SHOT_WIDTH = 400;
    private static final int SHOT_HEIGHT = 500;

    private static final float SHIP_WIDTH = 70;
    private static final float SHIP_HEIGHT = 70;
    private static final float SHIP_CENTER_WIDTH = SHIP_WIDTH / (float) 2;
    private static final float SHIP_CENTER_HEIGHT = SHIP_HEIGHT / (float) 2;

    private Texture img;
    private TextureRegion[] region = new TextureRegion[COUNT_SHOT];
    private TextureRegion heroShip;
    private Animation backgroundAnimation;

    private Vector2 pos;
    private Vector2 touch;
    private Vector2 moveToTouch;
    private Vector2 distanceToTouch;

    private boolean isWeHaveOrder;
    private boolean isMoveUp;
    private boolean isMoveDown;
    private boolean isMoveLeft;
    private boolean isMoveRight;

    private float stateTime;
    private float angle;
    private float rotateAngle;
    private float shipSpeed;
    private HeroShip myHeroShip;

    @Override
    public void show() {
        super.show();
        myHeroShip = new HeroShip();
        heroShip = myHeroShip.getHeroShip();
        shipSpeed = myHeroShip.getSpeed();

        animBackground();

        pos = new Vector2(100f, 100f);
        touch = new Vector2();
        moveToTouch = new Vector2(shipSpeed, shipSpeed);
        distanceToTouch = new Vector2();
    }

    private void animBackground() {
        img = new Texture("cosmos.png");
        for (int i = 0; i < COUNT_SHOT; i++) {
            region[i] = new TextureRegion(img, (STEP_NEXT_SHOT_WIDTH * i), 0, SHOT_WIDTH, SHOT_HEIGHT);
        }
        backgroundAnimation = new Animation(0.033f, region);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += Gdx.graphics.getDeltaTime();
        move();
        TextureRegion background = (TextureRegion) backgroundAnimation.getKeyFrame(stateTime, true);

        distanceToTouch.set(touch);
        if (isWeHaveOrder) {
            if (distanceToTouch.sub(pos).len() > shipSpeed){
                pos.add(moveToTouch);
                rotateAngle = (float) Math.atan2(touch.y - pos.y, touch.x - pos.x);
                angle = ((float) Math.toDegrees(rotateAngle)) - 90;
            } else {
                pos.set(touch);
                isWeHaveOrder = false;
            }
        }
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(heroShip, pos.x - SHIP_CENTER_WIDTH, pos.y - SHIP_CENTER_HEIGHT, SHIP_CENTER_WIDTH, SHIP_CENTER_HEIGHT, SHIP_WIDTH, SHIP_HEIGHT, 1,1, angle);
        batch.end();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
        img.dispose();
        myHeroShip.getShip().dispose();
    }

//    -----------------------------------------------------------------------------
//    Input methods

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 51:    // w
                isMoveUp = true;
                break;
            case 47:    // s
                isMoveDown = true;
                break;
            case 29:    // a
                isMoveLeft = true;
                break;
            case 32:    // d
                isMoveRight = true;
                break;
        }
        return false;
    }

    @Override
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

    private void move(){
        if (isMoveUp && (pos.y + SHIP_CENTER_HEIGHT < Gdx.graphics.getHeight())) {
            pos.add(0f, shipSpeed);
            angle = 0f;
        }
        if (isMoveDown && (pos.y - SHIP_CENTER_HEIGHT > 0)) {
            pos.add(0f, -shipSpeed);
            angle = 180f;
        }
        if (isMoveRight && (pos.x + SHIP_CENTER_WIDTH < Gdx.graphics.getWidth())) {
            pos.add(shipSpeed, 0f);
            angle = 270f;
        }
        if (isMoveLeft && (pos.x - SHIP_CENTER_WIDTH > 0)) {
            pos.add(-shipSpeed, 0f);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        moveToTouch.set(touch.cpy().sub(pos)).setLength(shipSpeed);
        isWeHaveOrder = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        moveToTouch.set(touch.cpy().sub(pos)).setLength(shipSpeed);
        isWeHaveOrder = true;
        return false;
    }
}
