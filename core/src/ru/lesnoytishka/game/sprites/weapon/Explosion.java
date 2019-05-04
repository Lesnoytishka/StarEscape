package ru.lesnoytishka.game.sprites.weapon;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.base.Sprite;

public class Explosion extends Sprite {
    private float animateInterval = 0.017f;
    private float animateTimer;
    private Sound explosionSound;

    public Explosion(TextureAtlas atlas, Sound explosionSound){
        super(atlas.findRegion("explosion7"), 6, 6, 30);
        this.explosionSound = explosionSound;
    }

    public void set(float height, Vector2 pos) {
        this.position.set(pos);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (frame == 1){
                explosionSound.play(0.15f);
            }
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
