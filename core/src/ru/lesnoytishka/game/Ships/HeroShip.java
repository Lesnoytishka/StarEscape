package ru.lesnoytishka.game.Ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.lesnoytishka.game.Base.BaseShip;

public class HeroShip extends BaseShip {

    public HeroShip(){
        hp = 200;
        speed = 7f;

        shipTexture = new Texture("heroShip.png");
        heroShip = new TextureRegion(shipTexture, 0, 0, 237, 290);

    }
}
