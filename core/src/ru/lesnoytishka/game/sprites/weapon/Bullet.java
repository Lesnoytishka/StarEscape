package ru.lesnoytishka.game.sprites.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.environment.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 speed;
    private int damage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
        speed = new Vector2();
    }

    public void set(
            Object owner,
            int damage,
            Vector2 speed,
            TextureRegion region,
            float height,
            Vector2 position,
            Rect worldBounds
    ){
        this.owner = owner;
        this.damage = damage;
        this.speed.set(speed);
        this.regions[0] = region;
        setHeightProportion(height);
        this.position.set(position);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        position.mulAdd(speed, delta);
        if (isOutside(worldBounds)){
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }
}
