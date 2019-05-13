package ru.lesnoytishka.game.sprites.GameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.lesnoytishka.game.base.Sprite;
import ru.lesnoytishka.game.utils.Rect;

public class HPbar extends Sprite {

    private Rect worldBounds;

    public HPbar(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("hpBar"), 3, 1, 3);
        setHeightProportion(0.03f);
        this.worldBounds = worldBounds;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
    }

    public void update(int hp, int maxHP) {
        float diffHP = hp / (float) maxHP;
        setWidth(worldBounds.getWidth() * diffHP);
        if (diffHP >= 0.7f){
            frame = 2;
        } else if (diffHP < 0.7f){
            frame = 1;
        }
        if (diffHP < 0.4f){
            frame = 0;
        }
    }
}
