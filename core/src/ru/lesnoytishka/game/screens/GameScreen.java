package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.lesnoytishka.game.base.BackgroundsObject;
import ru.lesnoytishka.game.base.BaseScreen;
import ru.lesnoytishka.game.base.BaseShip;
import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.environment.Rect;
import ru.lesnoytishka.game.environment.Rnd;
import ru.lesnoytishka.game.pools.BulletPool;
import ru.lesnoytishka.game.pools.ShipsPool;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundClouds;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundGameScreen;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundStars;
import ru.lesnoytishka.game.sprites.Ships.HeroShip;
import ru.lesnoytishka.game.sprites.Ships.LightShip;
import ru.lesnoytishka.game.sprites.weapon.Bullet;

public class GameScreen extends BaseScreen {

    private TextureAtlas atlas;
    private Texture bg;
    private BackgroundGameScreen background;

    private Music music;

    private List<BackgroundsObject> environment;

    private HeroShip heroShip;
    private BulletPool bulletPool;
    private ShipsPool shipsPool;
    private Vector2 spawnEnemyPosition;
    private float shipsSpawnTimer;
    private float shipsSpawnInterval = Rnd.getFloat(1f, 5f);

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/bgAtlas.pack");
        bg = new Texture("textures/gameBackground.png");
        background = new BackgroundGameScreen(new TextureRegion(bg));

        playMusic();
        createStarsAndClouds();

        bulletPool = new BulletPool();
        shipsPool = new ShipsPool(atlas, "enemy0", bulletPool);
        heroShip = new HeroShip(atlas, bulletPool);
        spawnEnemyPosition = new Vector2();
    }

    private void playMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    private void createStarsAndClouds() {
        BackgroundStars[] starsBlue = new BackgroundStars[35];
        BackgroundStars[] starsYellow = new BackgroundStars[20];
        BackgroundStars[] starsWhite = new BackgroundStars[50];
        BackgroundStars[] starsZero = new BackgroundStars[70];
        BackgroundClouds[] bgClouds = new BackgroundClouds[8];

        for (int i = 0; i < starsBlue.length; i++) {
            starsBlue[i] = new BackgroundStars(atlas, "bgStarBlue");
        }
        for (int i = 0; i < starsYellow.length; i++) {
            starsYellow[i] = new BackgroundStars(atlas, "bgStarYellow");
        }
        for (int i = 0; i < starsWhite.length; i++) {
            starsWhite[i] = new BackgroundStars(atlas, "bgStarWhite");
        }
        for (int i = 0; i < starsZero.length; i++) {
            starsZero[i] = new BackgroundStars(atlas, "bgStarZero");
        }
        for (int i = 0; i < bgClouds.length; i++) {
            bgClouds[i] = new BackgroundClouds(atlas, "bgCloud");
        }

        environment = new ArrayList<BackgroundsObject>();
        environment.addAll(Arrays.asList(starsBlue));
        environment.addAll(Arrays.asList(starsYellow));
        environment.addAll(Arrays.asList(starsWhite));
        environment.addAll(Arrays.asList(starsZero));
        environment.addAll(Arrays.asList(bgClouds));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        createEnemyShips(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyedActiveSprites();
        draw();
    }

    private void checkCollisions() {
        for (Object bullet : bulletPool.getActiveObjects()) {
            if (bullet instanceof Bullet) {
                if (!((Bullet) bullet).isOutside(heroShip) && !((Bullet) bullet).getOwner().equals(heroShip)) {
                    heroShip.takeDamage(((Bullet) bullet).getDamage());
                    ((Bullet) bullet).destroy();
                    System.err.println("hero hp = " + heroShip.getHp());
                }

                for (Object ship : shipsPool.getActiveObjects()) {
                    if (ship instanceof BaseShip) {
                        if ( !((Bullet) bullet).isOutside((BaseShip) ship) && ((Bullet) bullet).getOwner().equals(heroShip)) {
                            ((BaseShip) ship).takeDamage(((Bullet) bullet).getDamage());
                            ((Bullet) bullet).destroy();
                        }

                        if (!((BaseShip) ship).isOutside(heroShip)){
                            ((BaseShip) ship).takeDamage(15);
                            heroShip.takeDamage(15);
                        }
                    }
                }
            }
        }
    }

    private void update(float delta) {
        for (BackgroundsObject bgEnvi : environment) {
            bgEnvi.update(delta);
        }
        heroShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        shipsPool.updateActiveSprites(delta);
    }

    private void createEnemyShips(float delta) {
        shipsSpawnTimer += delta;
        if (shipsSpawnTimer >= shipsSpawnInterval) {
            LightShip lightShip = (LightShip) shipsPool.obtain();
            spawnEnemyPosition = new Vector2(Rnd.getFloat(
                    worldBounds.getLeft() + lightShip.halfWidth,
                    worldBounds.getRight() - lightShip.halfWidth),
                    worldBounds.getTop() + lightShip.halfHeight
            );
            lightShip.set(0.08f, spawnEnemyPosition, worldBounds);
            shipsSpawnTimer = 0f;
        }
    }

    private void freeAllDestroyedActiveSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        shipsPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (BackgroundsObject stars : environment) {
            if (stars instanceof BackgroundStars) {
                stars.draw(batch);
            }
        }
        heroShip.draw(batch);
        for (BackgroundsObject clouds : environment) {
            if (clouds instanceof BackgroundClouds) {
                clouds.draw(batch);
            }
        }
        bulletPool.drawActiveSprites(batch);
        shipsPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (BackgroundsObject bgEnvi : environment) {
            bgEnvi.resize(worldBounds);
        }
        heroShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        music.dispose();
        bulletPool.dispose();
        shipsPool.dispose();
        heroShip.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        heroShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        heroShip.touchDragged(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        heroShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        heroShip.keyUp(keycode);
        return super.keyUp(keycode);
    }
}
