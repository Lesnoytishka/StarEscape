package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final int COUNT_SHOT = 16;
    private static final int STEP_NEXT_SHOT_WIDTH = 400;
    private static final int SHOT_WIDTH = 400;
    private static final int SHOT_HEIGHT = 500;

    private static final int SHIP_WIDTH = 70;
    private static final int SHIP_HEIGHT = 70;
    private float shipWidth = SHIP_WIDTH / (float) 2;
    private float shipHeight = SHIP_HEIGHT / (float) 2;
    private float shipSpeed = 10;

    private Texture img;
    private Texture ship;
    private TextureRegion heroShip;
    private TextureRegion[] region = new TextureRegion[COUNT_SHOT];
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


    @Override
    public void show() {
        super.show();
//        HeroShip myHeroShip = new HeroShip();
//        heroShip = myHeroShip.heroShip;

        ship = new Texture("heroShip.png");
        heroShip = new TextureRegion(ship, 0, 0, 237, 290);

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

        if (isWeHaveOrder) {
            distanceToTouch.set(touch);
            if (distanceToTouch.sub(pos).len() > shipSpeed){
                pos.add(moveToTouch);
            } else {
                pos.set(touch);
            }
        }
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(heroShip, pos.x - shipWidth, pos.y - shipHeight, shipWidth, shipHeight, SHIP_WIDTH, SHIP_HEIGHT, 1,1, angle);
        batch.end();
        rotateAngle = (float) Math.atan2(touch.y - pos.y, touch.x - pos.x);
        angle = (float) Math.toDegrees(rotateAngle - 89);
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
        img.dispose();
        ship.dispose();
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
        if (isMoveUp && (pos.y + SHIP_HEIGHT < Gdx.graphics.getHeight())) {
            pos.add(0f, shipSpeed);
            angle = 0f;
        }
        if (isMoveDown && (pos.y > 0)) {
            pos.add(0f, -shipSpeed);
            angle = 180f;
        }
        if (isMoveRight && (pos.x + SHIP_WIDTH < Gdx.graphics.getWidth())) {
            pos.add(shipSpeed, 0f);
            angle = 270f;
        }
        if (isMoveLeft && (pos.x > 0)) {
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

//    -----------------------------------------------------------------------------
//    Disabled methods

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
