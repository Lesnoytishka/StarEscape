package ru.lesnoytishka.game.sprites.Ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


import ru.lesnoytishka.game.base.BaseShip;
import ru.lesnoytishka.game.environment.Rect;
import ru.lesnoytishka.game.pools.BulletPool;

public class LightShip extends BaseShip {

    public LightShip(TextureAtlas atlas, String path, BulletPool bullets) {
        super(atlas, path, bullets);
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
