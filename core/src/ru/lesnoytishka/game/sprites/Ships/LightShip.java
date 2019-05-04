package ru.lesnoytishka.game.sprites.Ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


import ru.lesnoytishka.game.base.BaseShip;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.pools.BulletPool;
import ru.lesnoytishka.game.pools.ExplosionPool;

public class LightShip extends BaseShip {

    public LightShip(TextureAtlas atlas, String path, BulletPool bullets, ExplosionPool explosionPool) {
        super(atlas, path, bullets, explosionPool);
        hp = 10;
        setHeightProportion(halfHeight);
    }

    public void set(
            float height,
            Vector2 position,
            Rect worldBounds
    ){
        setHeightProportion(height);
        this.position.set(position);
        this.worldBounds = worldBounds;
        hp = 10;
    }
}
