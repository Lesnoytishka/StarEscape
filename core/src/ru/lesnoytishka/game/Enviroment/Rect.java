package ru.lesnoytishka.game.Enviroment;

import com.badlogic.gdx.math.Vector2;

public class Rect {

    public final Vector2 position = new Vector2(); // позиция по центру
    public float halfWidth;     // половина ширины
    public float halfHeight;    // половина высоты

    public Rect() {

    }

    public Rect(Rect from) {
        this(from.position.x, from.position.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        position.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft() {
        return position.x - halfWidth;
    }

    public float getRight() {
        return position.x + halfWidth;
    }

    public float getTop() {
        return position.y + halfHeight;
    }

    public float getBottom() {
        return position.y - halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }

    public void set(Rect from) {
        position.set(from.position);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    public void setSize(float width, float height) {
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
    }

    public void setHeight(float height){
        halfHeight = height / 2f;
    }

    public void setWidth(float width){
        halfWidth = width / 2f;
    }

    public void setTop(float top) {
        position.y = top - halfHeight;
    }

    public void setBottom(float bottom) {
        position.y = bottom + halfHeight;
    }

    public void setLeft(float left) {
        position.x = left + halfWidth;
    }

    public void setRight(float right) {
        position.x = right - halfWidth;
    }

    public boolean isInside(Vector2 touch) {
        return ((touch.x >= getLeft()) && (touch.x <= getRight()) && (touch.y <= getTop()) && (touch.y >= touch.y));
    }

    public boolean isOutside(Rect other){
        return (getTop() < other.getBottom()) || (getBottom() > other.getTop()) || (getLeft() > other.getRight()) || (getRight() < other.getLeft());
    }

    @Override
    public String toString(){
        return "Rectangle= position: " + position + " size: (" + getWidth() + ", " + getHeight() + ")";
    }
}
