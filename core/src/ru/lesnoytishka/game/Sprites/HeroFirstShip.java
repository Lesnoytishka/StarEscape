package ru.lesnoytishka.game.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Base.Sprite;
import ru.lesnoytishka.game.Enviroment.Rect;

public class HeroFirstShip extends Sprite {

    private float speed = 0.03f;
    private Vector2 speedToMove = new Vector2(speed, speed);

    public HeroFirstShip(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.08f);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (touch.sub(position).len() > speed){
            position.add(speedToMove);
            speedToMove.set(touch.cpy().sub(position).setLength(speed));
        } else {
            position.set(touch);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }
}
