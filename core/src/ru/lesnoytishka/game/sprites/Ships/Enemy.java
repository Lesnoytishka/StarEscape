package ru.lesnoytishka.game.sprites.Ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.base.BaseShip;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.pools.BulletPool;
import ru.lesnoytishka.game.pools.ExplosionPool;

public class Enemy extends BaseShip {

    private enum State {DESCENT, FIGHT}

    private State state;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        state = State.DESCENT;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() <= worldBounds.getTop()) {
            speed.set(speedFight);
            state = State.FIGHT;
        }
        if (state == State.FIGHT) {
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                shoot();
            }
        } else {
            reloadTimer = 0f;
        }
    }

    public void set(
            TextureRegion regions,
            Vector2 speed,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            Sound shotSound,
            float height,
            int hp
    ) {
        this.regions = new TextureRegion[1];
        this.regions[0] = regions;
        this.speedFight.set(speed);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed.set(0, bulletVY);
        this.weaponDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.soundShot = shotSound;
        setHeightProportion(height);
        this.hp = hp;
        this.speed.set(descentSpeed);
        reloadTimer = reloadInterval;
        state = State.DESCENT;
    }

    @Override
    public void destroy() {
        super.destroy();
        detonation();
    }
}
