package ru.lesnoytishka.game.sprites.weapon;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.base.Sprite;

public class Explosion extends Sprite {
    private Sound explosionSound;
    private float animateInterval = 0.017f;
    private float animateTimer;

    public Explosion(TextureAtlas atlas, Sound sound){
        super(atlas.findRegion("explosion7"), 6, 6, 30);
        this.explosionSound = sound;
    }

    public void set(float height, Vector2 pos) {
        this.position.set(pos);
        setHeightProportion(height);
        explosionSound.play();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
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
