package ru.lesnoytishka.game.Sprites.Ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.lesnoytishka.game.Base.Sprite;

public class Bullets extends Sprite {

    private float damage = 15f;

    public Bullets(TextureRegion region) {
        super(region);
    }
}
