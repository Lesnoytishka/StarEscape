package ru.lesnoytishka.game.sprites.weapon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.sprites.Ships.HeroShip;
import ru.lesnoytishka.game.utils.Rnd;

public class BuffItem extends Sprite {
    enum BuffType{REPAIR_KIT, BULLET_ADD}

    private final TextureRegion REPAIR_KIT;
    private final TextureRegion BULLET_ADD;

    private int angle = 360;
    private final float SPEED = 0.35f;
    private BuffType buffType;
    private Vector2 followSpeed;
    private Vector2 followSpeedWihDelta;
    private Vector2 distanceToHeroShip;
    private HeroShip heroShip;

    public BuffItem(TextureAtlas atlas, HeroShip heroShip) {
        REPAIR_KIT = atlas.findRegion("repairKit");
        BULLET_ADD = atlas.findRegion("bulletAdd");
        this.heroShip = heroShip;
        followSpeed = new Vector2();
        followSpeedWihDelta = new Vector2();
        distanceToHeroShip = new Vector2();
    }

    public void set(BuffType buffType) {
        this.buffType = buffType;
        regions = new TextureRegion[1];
        switch (buffType){
            case REPAIR_KIT:
                regions[0] = REPAIR_KIT;
                break;
            case BULLET_ADD:
                regions[0] = BULLET_ADD;
                break;
        }
        setHeightProportion(0.2f);
    }

    @Override
    public void update(float delta) {
        setAngle(angle);
        angle--;
        if (angle <= 0) {
            angle = 360;
        }
        if (!isOutside(heroShip)) {
            followSpeed.set(heroShip.position.cpy().sub(position)).setLength(SPEED);
            distanceToHeroShip.set(heroShip.position);
            followSpeedWihDelta.set(followSpeed);
            followSpeedWihDelta.scl(delta);
            if (distanceToHeroShip.sub(position).len() > followSpeedWihDelta.len()) {
                position.mulAdd(followSpeed, delta);
            } else {
                position.set(heroShip.position);
                activateBuff();
                destroy();
            }
        }
    }

    private void activateBuff(){
        switch (buffType){
            case REPAIR_KIT:
                buffRepairKit();
                break;
            case BULLET_ADD:
                buffBulletAdd();
                break;
            default:
        }
    }

    private void buffRepairKit(){
        heroShip.setMaxHP(heroShip.getMaxHP() + 10);
        heroShip.setHP(heroShip.getMaxHP());
    }

    private void buffBulletAdd(){
        heroShip.setCountBullet(heroShip.getCountBullet() + 1);
    }
}
