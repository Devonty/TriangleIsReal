package com.cgvsu.rasterization;

public class Vector2f {
    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(final Vector2f v) {
        this(v.x, v.y);
    }

    public Vector2f() {
        this(0f, 0f);
    }

    public void add(final Vector2f other) {
        this.x += other.x;
        this.y += other.y;
    }
    public void add(final int x, final int y) {
        this.x += x;
        this.y += y;
    }

    public static Vector2f sum(final Vector2f v1, final Vector2f v2) {
        Vector2f result = new Vector2f(v1);
        result.add(v2);
        return result;
    }

    public static float dot(final Vector2f v1, final Vector2f v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public float dot(final Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static float getLength(final Vector2f v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public void normalize() {
        float length = getLength();
        x /= length;
        y /= length;
    }

    public static Vector2f getNormalized(final Vector2f v){
        float length = v.getLength();
        return new Vector2f(v.x / length, v.y / length);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }
}
