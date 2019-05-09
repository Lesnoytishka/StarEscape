package ru.lesnoytishka.game.sprites.weapon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.SupportItem;
import ru.lesnoytishka.game.sprites.Ships.HeroShip;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.utils.Rnd;

public class RepairKit extends SupportItem {

    private Rect worldBounds;

    public RepairKit(TextureAtlas atlas, Rect worldBounds, HeroShip heroShip) {
        super(atlas,"repairKit", heroShip);
        this.worldBounds = worldBounds;
    }

    public void set(){
        position.set(Rnd.getFloat(worldBounds.getLeft(), worldBounds.getRight()), Rnd.getFloat(worldBounds.getBottom(), worldBounds.getTop()));
    }

    @Override
    protected void activateBuff(){
        heroShip.setMaxHP(heroShip.getMaxHP() + 10);
        heroShip.setHP(heroShip.getMaxHP());
    }
}
