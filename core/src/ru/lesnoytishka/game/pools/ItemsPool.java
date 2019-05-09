package ru.lesnoytishka.game.pools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.SpritesPool;
import ru.lesnoytishka.game.base.SupportItem;
import ru.lesnoytishka.game.sprites.Ships.HeroShip;
import ru.lesnoytishka.game.sprites.weapon.RepairKit;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.utils.Rnd;

public class ItemsPool extends SpritesPool<SupportItem> {

    private TextureAtlas atlas;
    private HeroShip heroShip;
    private Rect worldBounds;
    private float generateInterval = Rnd.getFloat(1f, 2);
    private float generateTimer;

    public ItemsPool(TextureAtlas atlas, HeroShip heroShip, Rect worldBounds) {
        this.atlas = atlas;
        this.heroShip = heroShip;
        this.worldBounds = worldBounds;
    }

    public void update(float delta){
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            RepairKit repairKit = (RepairKit) obtain();
            repairKit.set();
            generateTimer = 0f;
            generateInterval = Rnd.getFloat(4f, 76f);
        }
    }

    @Override
    protected SupportItem newObject() {
        return new RepairKit(atlas, worldBounds, heroShip);
    }
}
