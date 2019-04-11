package ru.lesnoytishka.game.Ships;

import com.badlogic.gdx.graphics.Texture;

import ru.lesnoytishka.game.Base.BaseShip;

public class HeroShip extends BaseShip {

    private Texture ship = new Texture("heroesShip");

    HeroShip(){
        hp = 200;
        speed = 5f;
    }

}
