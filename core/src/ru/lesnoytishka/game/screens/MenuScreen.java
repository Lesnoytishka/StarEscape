package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Locale;

import ru.lesnoytishka.game.Base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final int COUNT_SHOT = 16;
    private static final int STEP_NEXT_SHOT_WIDTH = 400;
    private static final int SHOT_WIDTH = 400;
    private static final int SHOT_HEIGHT = 500;

    private static final int SHIP_WIDTH = 70;
    private static final int SHIP_HEIGHT = 70;

    private Texture img;
    private Texture heroShip;
    private TextureRegion[] region = new TextureRegion[COUNT_SHOT];
    private Animation backgroundAnimation;

    private boolean weHaveOrder;

    private Vector2 pos;
    private Vector2 touch;

    private float stateTime;

    @Override
    public void show() {
        super.show();
        img = new Texture("cosmos.png");
        heroShip = new Texture("heroShip.png");

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
        TextureRegion background = (TextureRegion) backgroundAnimation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(heroShip, pos.x, pos.y, SHIP_WIDTH, SHIP_HEIGHT);
        batch.end();

        if (weHaveOrder) {
            float positionX = pos.x + (SHIP_WIDTH / 2.0f);
            float positionY = pos.y + (SHIP_HEIGHT / 2.0f);

            Vector2 moveToVector = new Vector2(touch.x - positionX, touch.y - positionY);
            float lengthMovingDist = (float) Math.sqrt( (Math.pow(moveToVector.x, 2) + Math.pow(moveToVector.y, 2)) );
            Vector2 normalityMove = new Vector2(moveToVector.x / lengthMovingDist, moveToVector.y / lengthMovingDist);

            touchedDownMove(positionX, positionY, normalityMove);
        }
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
        img.dispose();
        heroShip.dispose();
    }

//    -----------------------------------------------------------------------------
//    Input methods

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 19:
                pos.add(0f, 10f);
                break;
            case 20:
                pos.add(0f, -10f);
                break;
            case 21:
                pos.add(-10f, 0f);
                break;
            case 22:
                pos.add(10f, 0f);
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case 'w':
                if (pos.y + SHIP_HEIGHT < Gdx.graphics.getHeight()) {
                    pos.add(0f, 10f);
                }
                break;
            case 's':
                if (pos.y > 0) {
                    pos.add(0f, -10f);
                }
                break;
            case 'a':
                if (pos.x > 0) {
                    pos.add(-10f, 0f);
                }
                break;
            case 'd':
                if (pos.x + SHIP_WIDTH < Gdx.graphics.getWidth()) {
                    pos.add(10f, 0f);
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        weHaveOrder = true;
        return false;
    }

    private void touchedDownMove(float positionX, float positionY, Vector2 normalityMove){
        float spread = 1f;
        float shipSpeed = 2f;
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
        weHaveOrder = !shipIntoTouchedPosition;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println(String.format(Locale.US,"x = %d y = %d point = %d btn = %d", screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
