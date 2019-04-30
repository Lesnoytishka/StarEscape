package ru.lesnoytishka.game.pools;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.base.SpritesPool;
import ru.lesnoytishka.game.sprites.weapon.Bullet;

public class BulletPool extends SpritesPool {

    @Override
    protected Sprite newObject() {
        return new Bullet();
    }
}
