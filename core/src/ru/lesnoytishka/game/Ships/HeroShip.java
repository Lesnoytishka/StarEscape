package ru.lesnoytishka.game.Ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.lesnoytishka.game.Base.BaseShip;

public class HeroShip extends BaseShip {

    private Texture ship = new Texture("heroesShip");
    public TextureRegion heroShip;

    public HeroShip(){
        hp = 200;
        speed = 5f;

        ship = new Texture("heroShip.png");
        heroShip = new TextureRegion(ship, 0, 0, 237, 290);

    }

}
