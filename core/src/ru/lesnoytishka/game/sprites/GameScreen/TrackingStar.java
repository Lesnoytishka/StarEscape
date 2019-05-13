package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackingStar extends BackgroundStars {

    private final Vector2 trackingSpeed;
    private final Vector2 sumSpeed = new Vector2();

    public TrackingStar(TextureAtlas atlas, String starName, Vector2 trackingSpeed) {
        super(atlas, starName);
        this.trackingSpeed = trackingSpeed;
    }

    @Override
    public void update(float delta) {
        sumSpeed.setZero().mulAdd(trackingSpeed, 0.2f).rotate(180).add(speed);
        position.mulAdd(sumSpeed, delta);
        checkBounds();
    }
}
