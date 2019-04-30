package ru.lesnoytishka.game.pools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.base.SpritesPool;
import ru.lesnoytishka.game.sprites.Ships.LightShip;

public class ShipsPool extends SpritesPool {

    private TextureAtlas atlas;
    private String path;
    private BulletPool bullets;

    public ShipsPool(TextureAtlas atlas, String path, BulletPool bullets){
        this.atlas = atlas;
        this.path = path;
        this.bullets = bullets;
    }

    @Override
    protected Sprite newObject() {
        return new LightShip(atlas, path, bullets);
    }
}
