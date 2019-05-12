package ru.lesnoytishka.game.sprites.weapon;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.pools.ItemsPool;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.utils.Rnd;

public class BuffItemGenerator extends Sprite {

    private ItemsPool itemsPool;
    private Rect worldBounds;

    private float generateInterval = Rnd.getFloat(3f, 72f);;
    private float generateTimer;

    public BuffItemGenerator(ItemsPool itemsPool, Rect worldBounds) {
        this.itemsPool = itemsPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            BuffItem buffItem = itemsPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                buffItem.set(BuffItem.BuffType.REPAIR_KIT);
            } else {
                buffItem.set(BuffItem.BuffType.BULLET_ADD);
            }
            buffItem.position.set(Rnd.getFloat(worldBounds.getLeft(), worldBounds.getRight()),
                    Rnd.getFloat(worldBounds.getBottom() + 0.035f, worldBounds.getTop()));
        }
    }
}
