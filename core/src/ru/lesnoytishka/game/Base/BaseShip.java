package ru.lesnoytishka.game.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.Enviroment.Rect;

public abstract class BaseShip extends Sprite {

    private float hp = 10f;
    private Vector2 speed = new Vector2(0, 0.2f);
    private Sound soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    private Sound soundShot = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));

    public BaseShip(TextureRegion region) {
        super(region);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }

    public void dispose(){
        soundExplosion.dispose();
        soundShot.dispose();
    }

    @Override
    public boolean isInside(Vector2 touch) {
        return super.isInside(touch);
    }

    @Override
    public boolean isOutside(Rect other) {
        return super.isOutside(other);
    }
}
