package ru.lesnoytishka.game.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.sprites.Ships.HeroShip;

public class SupportItem extends Sprite {

    private int angle = 360;

    public SupportItem(TextureAtlas atlas, String itemName) {
        super(atlas.findRegion(itemName));
        setHeightProportion(0.2f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setAngle(angle);
        angle--;
        if (angle <= 0) {
            angle = 360;
        }
    }

    public void checkCollision(HeroShip heroShip){
        if (!isOutside(heroShip)) {
            destroy();
        }
    }
}
