package ru.lesnoytishka.game.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.Sprite;
import ru.lesnoytishka.game.Enviroment.Rect;

public class HeroFirstShip extends Sprite {

    private float speed = 0.003f;
    private Vector2 speedToMove = new Vector2(speed, speed);
    private Vector2 touch = new Vector2();

    public HeroFirstShip(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.08f);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        speedToMove.set(touch.cpy().sub(position)).setLength(speed);
        System.out.println(touch.x + " " + position.x);
        return false;
    }

    @Override
    public void update(float delta) {
        if (touch.sub(position).len() > speed){
            position.add(speedToMove);
        } else {
            position.set(touch);
        }
    }
}
