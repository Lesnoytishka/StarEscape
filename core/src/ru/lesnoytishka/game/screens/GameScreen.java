package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import ru.lesnoytishka.game.environment.Rect;
import ru.lesnoytishka.game.environment.Rnd;
import ru.lesnoytishka.game.pools.BulletPool;
import ru.lesnoytishka.game.pools.ShipsPool;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundClouds;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundGameScreen;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundStars;
import ru.lesnoytishka.game.sprites.GameScreen.GameOver;
import ru.lesnoytishka.game.sprites.GameScreen.NewGame;
import ru.lesnoytishka.game.sprites.Ships.HeroShip;
import ru.lesnoytishka.game.sprites.Ships.LightShip;
import ru.lesnoytishka.game.sprites.weapon.Bullet;

public class GameScreen extends BaseScreen {

    private Game game;
    private GameOver gameOver;
    private NewGame newGame;
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
    private boolean isInfoPressed = true;
    private int score;

    public GameScreen(Game game) {
        this.game = game;
    }

//    ----------------------------------------------------------------------------------------------
//    initialization

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/bgAtlas.pack");
        bg = new Texture("textures/gameBackground.png");
        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, game);
        background = new BackgroundGameScreen(new TextureRegion(bg));

        playMusic();
        createStarsAndClouds();

        bulletPool = new BulletPool();
        shipsPool = new ShipsPool(atlas, "enemyLight1", bulletPool);
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

//    ----------------------------------------------------------------------------------------------
//    render

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyedActiveSprites();
        draw();

        if (!isInfoPressed) {
            Gdx.graphics.setTitle("" +
                    "[hero hp = " + heroShip.getHp() + "]" +
                    " [BulletPool act/free " + bulletPool.getActiveObjects().size() + " / " + bulletPool.getFreeObjects().size() + "]" +
                    " [ShipPool act/free " + shipsPool.getActiveObjects().size() + " / " + shipsPool.getFreeObjects().size() + "]" +
                    " [fps: " + Gdx.graphics.getFramesPerSecond() + "]"
            );
        } else {
            Gdx.graphics.setTitle("StarEscape [hero hp = " + heroShip.getHp() + "] [score = " + score + "]");
        }
    }

    private void checkCollisions() {
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<LightShip> enemiesList = shipsPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (!bullet.isDestroyed() && !bullet.isOutside(heroShip) && !bullet.getOwner().equals(heroShip)) {
                heroShip.takeDamage(bullet.getDamage());
                bullet.destroy();
                System.err.println("hero hp = " + heroShip.getHp());
            }
            for (LightShip ship : enemiesList) {
                if (ship.isDestroyed()) {
                    continue;
                }
                if (!bullet.isOutside(ship) && bullet.getOwner().equals(heroShip)) {
                    ship.takeDamage(bullet.getDamage());
                    bullet.destroy();
                    score++;
                }
                if (!ship.isOutside(heroShip)) {
                    ship.takeDamage(1);
                    heroShip.takeDamage(1);
                }
            }
        }
    }

    private void update(float delta) {
        for (BackgroundsObject bgEnvi : environment) {
            bgEnvi.update(delta);
        }
        if (heroShip.getHp() > 0) {
            createEnemyShips(delta);
            heroShip.update(delta);
            shipsPool.updateActiveSprites(delta);
        }
        bulletPool.updateActiveSprites(delta);
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
        if (heroShip.getHp() > 0) {
            heroShip.draw(batch);
            for (BackgroundsObject clouds : environment) {
                if (clouds instanceof BackgroundClouds) {
                    clouds.draw(batch);
                }
            }
            bulletPool.drawActiveSprites(batch);
            shipsPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
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
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
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

//    ----------------------------------------------------------------------------------------------
//    input

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (!heroShip.isDestroyed()) {
            heroShip.touchDown(touch, pointer);
        } else {
            newGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        newGame.touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (!heroShip.isDestroyed()) {
            heroShip.touchDragged(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!heroShip.isDestroyed()) {
            heroShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!heroShip.isDestroyed()) {
            heroShip.keyUp(keycode);
        }
        if (keycode == Input.Keys.Z) {
            isInfoPressed = !isInfoPressed;
        }
        return false;
    }
}
