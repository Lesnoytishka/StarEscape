package ru.lesnoytishka.game.sprites.Ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.environment.Rect;
import ru.lesnoytishka.game.environment.Rnd;
import ru.lesnoytishka.game.pools.ShipsPool;

public class EnemyGenerator {
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.1f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 10;

    private Rect worldBounds;

    private float generateInterval = 4f;
    private float generateTimer;

    private final TextureRegion enemySmallRegion;
//    private final TextureRegion enemyMediumRegion;
//    private final TextureRegion enemyBigRegion;

    private final Vector2 enemySmallV = new Vector2(0, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0, -0.03f);
    private final Vector2 enemyBigV = new Vector2(0, -0.005f);

    private final TextureRegion bulletRegion;

    private final ShipsPool enemyPool;

    public EnemyGenerator(TextureAtlas atlas, ShipsPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0 = atlas.findRegion("enemyLight");
        this.enemySmallRegion = atlas.findRegion("enemyLight");
//        TextureRegion enemy1 = atlas.findRegion("enemy1");
//        this.enemyMediumRegion =
//        TextureRegion enemy2 = atlas.findRegion("enemy2");
//        this.enemyBigRegion =
        this.bulletRegion = atlas.findRegion("beams0");
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = (Enemy) enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP
                );
//            } else if (type < 0.85f) {
//                enemy.set(
//                        enemyMediumRegion,
//                        enemyMediumV,
//                        bulletRegion,
//                        ENEMY_MEDIUM_BULLET_HEIGHT,
//                        ENEMY_MEDIUM_BULLET_VY,
//                        ENEMY_MEDIUM_DAMAGE,
//                        ENEMY_MEDIUM_RELOAD_INTERVAL,
//                        ENEMY_MEDIUM_HEIGHT,
//                        ENEMY_MEDIUM_HP
//                );
//            } else {
//                enemy.set(
//                        enemyBigRegion,
//                        enemyBigV,
//                        bulletRegion,
//                        ENEMY_BIG_BULLET_HEIGHT,
//                        ENEMY_BIG_BULLET_VY,
//                        ENEMY_BIG_DAMAGE,
//                        ENEMY_BIG_RELOAD_INTERVAL,
//                        ENEMY_BIG_HEIGHT,
//                        ENEMY_BIG_HP
//                );
            }
            enemy.position.x = Rnd.getFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
