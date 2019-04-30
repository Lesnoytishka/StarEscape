package ru.lesnoytishka.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class BaseBullets extends Sprite {

    private float damage = 15f;

    public BaseBullets(TextureRegion region) {
        super(region);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
