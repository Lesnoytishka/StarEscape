package ru.lesnoytishka.game.Base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseShip {

    protected float hp = 1f;
    protected float speed = 1.0f;
    protected Vector2 position = new Vector2(1.0f, 1.0f);
    protected Texture shipTexture;
    protected TextureRegion heroShip;

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Texture getShip() {
        return shipTexture;
    }

    public void setShip(Texture ship) {
        this.shipTexture = ship;
    }

    public TextureRegion getHeroShip() {
        return heroShip;
    }

    public void setHeroShip(TextureRegion heroShip) {
        this.heroShip = heroShip;
    }
}
