package ru.lesnoytishka.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.lesnoytishka.game.environment.Rect;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected TextureRegion animatedRegion;
    protected int frame = 0;
    protected Animation animation;
    protected float stateTime;

    private boolean isDestroyed;

    public Sprite() {
    }

    public Sprite(TextureRegion region){
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(Texture texture, int count, int width, int height){
        this.regions = new TextureRegion[count];
        frame = count - 1;
        for (int i = 0; i < count; i++) {
            this.regions[i] = new TextureRegion(texture, width * i, 0, width, height);
        }
        animation = new Animation(0.033f, regions);
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void draw(SpriteBatch batch){
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public void drawAnimated(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        animatedRegion = (TextureRegion) animation.getKeyFrame(stateTime, true);

        batch.draw(
                animatedRegion,
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public void update(float delta){

    }

    public void resize(Rect worldBounds){

    }

    public boolean touchUp(Vector2 touch, int pointer){
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer){
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer){
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

//    ------------------------------------------------------------------------------------------

    public void destroy(){
        isDestroyed = true;
    }

    public void flushDestroy(){
        isDestroyed = false;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }
}
