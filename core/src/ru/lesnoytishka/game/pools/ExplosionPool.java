package ru.lesnoytishka.game.pools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.base.SpritesPool;
import ru.lesnoytishka.game.sprites.weapon.Explosion;

public class ExplosionPool extends SpritesPool {

    TextureAtlas atlas;
    Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound sound){
        this.atlas = atlas;
        this.explosionSound = sound;
    }

    @Override
    protected Sprite newObject() {
        return new Explosion(atlas, explosionSound);

    }
}
