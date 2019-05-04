package ru.lesnoytishka.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.lesnoytishka.game.base.BackgroundsObject;
import ru.lesnoytishka.game.base.BaseScreen;
import ru.lesnoytishka.game.pools.ItemsPool;
import ru.lesnoytishka.game.sprites.GameScreen.HPbar;
import ru.lesnoytishka.game.sprites.weapon.RepairKit;
import ru.lesnoytishka.game.utils.Font;
import ru.lesnoytishka.game.utils.Rect;
import ru.lesnoytishka.game.pools.BulletPool;
import ru.lesnoytishka.game.pools.ExplosionPool;
import ru.lesnoytishka.game.pools.EnemyPool;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundClouds;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundGameScreen;
import ru.lesnoytishka.game.sprites.GameScreen.BackgroundStars;
import ru.lesnoytishka.game.sprites.GameScreen.GameOver;
import ru.lesnoytishka.game.sprites.GameScreen.NewGame;
import ru.lesnoytishka.game.sprites.Ships.Enemy;
import ru.lesnoytishka.game.sprites.Ships.EnemyGenerator;
import ru.lesnoytishka.game.sprites.Ships.HeroShip;
import ru.lesnoytishka.game.sprites.weapon.Bullet;

public class GameScreen extends BaseScreen {

    private final String SCORE = "Score: ";
    private final String HP = "HP: ";

    private GameOver gameOver;
    private NewGame newGame;
    private TextureAtlas atlas;
    private Texture bg;
    private BackgroundGameScreen background;
    private Music music;
    private Sound enemyShotSound;
    private List<BackgroundsObject> environment;
    private HeroShip heroShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyGenerator enemyGenerator;
    private ItemsPool itemsPool;
    private boolean isInfoPressed = true;
    private int score;
    private Font font;
    private StringBuilder sbScore;
    private StringBuilder sbHp;
    private HPbar hPbar;

    private RepairKit repairKit;

//    ----------------------------------------------------------------------------------------------
//    initialization

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/bgAtlas.pack");
        bg = new Texture("textures/gameBackground.png");
        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, this);
        background = new BackgroundGameScreen(new TextureRegion(bg));

        playMusic();
        createStarsAndClouds();

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        heroShip = new HeroShip(atlas, bulletPool, explosionPool, worldBounds);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, enemyShotSound, worldBounds);
        itemsPool = new ItemsPool(atlas, heroShip, worldBounds);

        font = new Font("font/words.fnt", "font/words.png");
        font.setFontSize(0.03f);
        sbScore = new StringBuilder();
        sbHp = new StringBuilder();
        hPbar = new HPbar(atlas, worldBounds);
    }

    private void playMusic() {
        enemyShotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
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
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (BackgroundsObject bgEnvi : environment) {
            bgEnvi.resize(worldBounds);
        }
        heroShip.resize(worldBounds);
        hPbar.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    public void reset(){
        playingStatus = PlayingStatus.PLAYING;
        heroShip.reset();
        score = 0;
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        bulletPool.freeAllActiveSprites();
        itemsPool.freeAllActiveSprites();
    }

//    ----------------------------------------------------------------------------------------------
//    render

    @Override
    public void render(float delta) {
        if (playingStatus != PlayingStatus.PASUSE) {
            super.render(delta);
            update(delta);
            checkCollisions();
            freeAllDestroyedActiveSprites();
            draw();
        }
        if (!isInfoPressed) {
            Gdx.graphics.setTitle("" +
                    "[hero hp = " + heroShip.getHp() + "]" +
                    " [BulletPool act/free " + bulletPool.getActiveObjects().size() + " / " + bulletPool.getFreeObjects().size() + "]" +
                    " [ShipPool act/free " + enemyPool.getActiveObjects().size() + " / " + enemyPool.getFreeObjects().size() + "]" +
                    " [fps: " + Gdx.graphics.getFramesPerSecond() + "]"
            );
        } else {
            Gdx.graphics.setTitle("StarEscape [hero hp = " + heroShip.getHp() + "] [score = " + score + "]");
        }
    }

    private void checkCollisions() {
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<Enemy> enemiesList = enemyPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (!bullet.isDestroyed() && !bullet.isOutside(heroShip) && !bullet.getOwner().equals(heroShip)) {
                heroShip.takeDamage(bullet.getDamage());
                bullet.destroy();
                System.err.println("hero hp = " + heroShip.getHp());
            }
            for (Enemy ship : enemiesList) {
                if (ship.isDestroyed()) {
                    continue;
                }
                if (!bullet.isOutside(ship) && bullet.getOwner().equals(heroShip) && !bullet.isDestroyed()) {
                    ship.takeDamage(bullet.getDamage());
                    if (ship.isDestroyed()) {
                        ship.detonation();
                        score++;
                    }
                    bullet.destroy();
                }
                if (!ship.isOutside(heroShip) && !ship.isDestroyed()) {
                    ship.takeDamage(1);
                    heroShip.takeDamage(1);
                }
            }
        }
        itemsPool.checkCollisions();
    }

    private void update(float delta) {
        for (BackgroundsObject bgEnvi : environment) {
            bgEnvi.update(delta);
        }
        if (heroShip.getHp() > 0) {
            itemsPool.updateActiveSprites(delta);
            itemsPool.update(delta);
            heroShip.update(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta);
            hPbar.update(heroShip.getHp(), heroShip.getMaxHP());
        }
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
    }

    private void freeAllDestroyedActiveSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        itemsPool.freeAllDestroyedActiveSprites();
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
            itemsPool.drawActiveSprites(batch);
            heroShip.draw(batch);
            for (BackgroundsObject clouds : environment) {
                if (clouds instanceof BackgroundClouds) {
                    clouds.draw(batch);
                }
            }
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            hPbar.draw(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbScore.setLength(0);
        sbHp.setLength(0);
        font.draw(batch, sbScore.append(SCORE).append(score), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHp.append(HP).append(heroShip.getHp()).append(" / ").append(heroShip.getMaxHP()),
                -0.1f, worldBounds.getBottom() + 0.03f);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        music.dispose();
        enemyShotSound.dispose();
        itemsPool.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        heroShip.dispose();
        font.dispose();
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
