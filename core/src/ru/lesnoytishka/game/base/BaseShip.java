package ru.lesnoytishka.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.environment.Rect;
import ru.lesnoytishka.game.pools.BulletPool;
import ru.lesnoytishka.game.sprites.weapon.Bullet;

public abstract class BaseShip extends Sprite {

    protected int hp = 10;
    protected float speedSheep = 0.2f;
    protected int weaponDamage;
    protected float bulletHeight;
    protected Vector2 speed = new Vector2(0, -speedSheep);

    protected Vector2 bulletSpeed;
    protected Vector2 bulletPosition;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    protected float reloadInterval = 0.8f;
    protected float reloadTimer;

    protected Sound soundShot;
    protected Sound soundExplosion;

    public BaseShip(TextureAtlas atlas, String path, BulletPool bullets) {
        super(atlas.findRegion(path));
        setWeapon(atlas, bullets);
        setSounds();
    }

    protected void setWeapon(TextureAtlas atlas, BulletPool bullets) {
        weaponDamage = 10;
        bulletHeight = 0.05f;
        bulletPool = bullets;
        bulletRegion = atlas.findRegion("beams0");
        bulletSpeed = new Vector2(0, -0.6f);
    }

    protected void setSounds() {
        soundShot = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    public void update(float delta) {
        checkingLive();
        shooting(delta);
        move(delta);
    }

    protected void checkingLive(){
        if (hp <= 0){
            soundExplosion.play(0.5f);
            destroy();
        }
    }

    protected void shooting(float delta) {
        bulletPosition = new Vector2(position.x, position.y - halfHeight);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
    }

    protected void move(float delta){
        position.mulAdd(speed, delta);
        if (isOutside(worldBounds)){
            destroy();
        }
    }

    private void shoot() {
        Bullet bullet = (Bullet) bulletPool.obtain();
        bullet.set(this, weaponDamage, bulletSpeed, bulletRegion, bulletHeight, bulletPosition, worldBounds);
        soundShot.play(0.03f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(worldBounds.getHeight() * 0.08f);
    }

    public void dispose(){
        soundExplosion.dispose();
        soundShot.dispose();
    }
}
