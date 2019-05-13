package ru.lesnoytishka.game.pools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.SpritesPool;
import ru.lesnoytishka.game.sprites.Ships.HeroShip;
import ru.lesnoytishka.game.sprites.weapon.BuffItem;

public class ItemsPool extends SpritesPool<BuffItem> {

    private TextureAtlas atlas;
    private HeroShip heroShip;

    public ItemsPool(TextureAtlas atlas, HeroShip heroShip) {
        this.atlas = atlas;
        this.heroShip = heroShip;
    }

    @Override
    protected BuffItem newObject() {
        return new BuffItem(atlas, heroShip);
    }
}
