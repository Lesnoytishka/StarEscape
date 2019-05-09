package ru.lesnoytishka.game.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.sprites.Ships.HeroShip;

public class SupportItem extends Sprite {

    private int angle = 360;
    private final float SPEED = 0.35f;
    private Vector2 followSpeed;
    private Vector2 followSpeedWihDelta;
    private Vector2 distanceToHeroShip;
    protected HeroShip heroShip;

    public SupportItem(TextureAtlas atlas, String itemName, HeroShip heroShip) {
        super(atlas.findRegion(itemName));
        setHeightProportion(0.2f);
        followSpeed = new Vector2();
        followSpeedWihDelta = new Vector2();
        distanceToHeroShip = new Vector2();
        this.heroShip = heroShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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

    protected void activateBuff(){

    }
}
