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
    private float shipSpeed = 4;

    private Texture img;
    private Texture ship;
    private TextureRegion heroShip;
    private TextureRegion[] region = new TextureRegion[COUNT_SHOT];
    private Animation backgroundAnimation;

    private Vector2 pos;
    private Vector2 touch;

    private boolean isWeHaveOrder;
    private boolean isMoveUp;
    private boolean isMoveDown;
    private boolean isMoveLeft;
    private boolean isMoveRight;

    private float stateTime;
    private float angle;

    @Override
    public void show() {
        super.show();
        img = new Texture("cosmos.png");
        ship = new Texture("heroShip.png");
        heroShip = new TextureRegion(ship, 0, 0, 237, 290);

        for (int i = 0; i < COUNT_SHOT; i++) {
            region[i] = new TextureRegion(img, (STEP_NEXT_SHOT_WIDTH * i), 0, SHOT_WIDTH, SHOT_HEIGHT);
        }
        backgroundAnimation = new Animation(0.033f, region);

        pos = new Vector2(100f, 100f);
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stateTime += Gdx.graphics.getDeltaTime();
        move();
        TextureRegion background = (TextureRegion) backgroundAnimation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(heroShip, pos.x, pos.y, (SHIP_WIDTH / 2.0f), (SHIP_HEIGHT / 2.0f), SHIP_WIDTH, SHIP_HEIGHT, 1,1, angle);

        if (isWeHaveOrder) {
            float positionX = pos.x + (SHIP_WIDTH / 2.0f);
            float positionY = pos.y + (SHIP_HEIGHT / 2.0f);
            Vector2 moveToVector = new Vector2(touch.x - positionX, touch.y - positionY);
            float lengthMovingDist = (float) Math.sqrt( (Math.pow(moveToVector.x, 2) + Math.pow(moveToVector.y, 2)) );
            Vector2 normalityMove = new Vector2(moveToVector.x / lengthMovingDist, moveToVector.y / lengthMovingDist);
            touchedDownMove(positionX, positionY, normalityMove);
        }

        batch.end();
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
        isWeHaveOrder = true;
        return false;
    }

    private void touchedDownMove(float positionX, float positionY, Vector2 normalityMove){
        float shipSpeed = 7f;
        float spread = 1f + shipSpeed;
        boolean shipIntoTouchedPosition = (((pos.x + (SHIP_WIDTH / 2.0f)) > (touch.x - spread)) && ((pos.x + (SHIP_WIDTH / 2.0f)) < (touch.x + spread))) &&
                (((pos.y + (SHIP_HEIGHT / 2.0f)) > (touch.y - spread)) && ((pos.y + (SHIP_HEIGHT / 2.0f)) < (touch.y + spread)));



        if (positionX < touch.x) {
            pos.x += ( shipSpeed * normalityMove.x);
        } else if (positionX > touch.x) {
            pos.x -= ( shipSpeed * Math.abs(normalityMove.x));
        }

        if (positionY < touch.y) {
            pos.y += ( shipSpeed * normalityMove.y);
        } else if (positionY > touch.y) {
            pos.y -= ( shipSpeed * Math.abs(normalityMove.y));
        }

        isWeHaveOrder = !shipIntoTouchedPosition;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
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
