package ru.lesnoytishka.game.pools;

import ru.lesnoytishka.game.base.SpritesPool;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.sprites.Ships.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bullets;
    private ExplosionPool explosionPool;
    private Rect worldBound;

    public EnemyPool(BulletPool bullets, ExplosionPool explosionPool, Rect worldBound){
        this.bullets = bullets;
        this.explosionPool = explosionPool;
        this.worldBound = worldBound;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bullets, explosionPool, worldBound);
    }
}
