package engine.math;

/**
 * Created by Deven on 12/21/2017.
 * Last edited on 6/11/2018.
 */

public class Vector2f {
    public float x, y;

    public Vector2f() {
        set(0, 0);
    }

    public Vector2f(float x, float y) {
        set(x, y);
    }

    public Vector2f(Vector2f v) {
        set(v);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2f v) {
        set(v.x, v.y);
    }

    public Vector2f add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2f add(Vector2f v) {
        return add(v.x, v.y);
    }

    public Vector2f sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2f sub(Vector2f v) {
        return sub(v.x, v.y);
    }

    public Vector2f mul(float f) {
        return mul(f, f);
    }

    public Vector2f mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2f mul(Vector2f v) {
        return mul(v.x, v.y);
    }

    public Vector2f crossMul(Vector2f v) {
        return mul(v.y, v.x);
    }

    public float dotProduct(Vector2f v) {
        return (x * v.x) + (y * v.y);
    }

    public Vector2f div(float f) {
        return div(f, f);
    }

    public Vector2f div(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Vector2f div(Vector2f v) {
        return div(v.x, v.y);
    }

    public Vector2f neg() {
        x = -x;
        y = -y;
        return this;
    }

    public float dst(float x, float y) {
        return MathUtils.sqrt(dstSq(x, y));
    }

    public float dst(Vector2f v) {
        return dst(v.x, v.y);
    }

    public float dstSq(float x, float y) {
        return (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y);
    }

    public float dstSq(Vector2f v) {
        return dstSq(v.x, v.y);
    }

    public float length() {
        return MathUtils.sqrt(lengthSq());
    }

    public float lengthSq() {
        return (x * x) + (y * y);
    }

    public void perp() {
        float temp = -x;
        x = y;
        y = temp;
    }

    public void normalize() {
        float lenSq = lengthSq();
        if (lenSq > 0.0001f * 0.0001f) {
            float invLen = 1.0f / (float) StrictMath.sqrt(lenSq);
            mul(invLen);
        }
    }

    @Override
    public Vector2f clone() {
        return new Vector2f(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // STATIC METHODS
    public static Vector2f add(Vector2f a, Vector2f b) {
        return new Vector2f(a.x + b.x, a.y + b.y);
    }

    public static Vector2f sub(Vector2f a, Vector2f b) {
        return new Vector2f(a.x - b.x, a.y - b.y);
    }

    public static Vector2f mul(Vector2f v, float f) {
        return new Vector2f(v.x * f, v.y * f);
    }

    public static Vector2f mul(Vector2f a, Vector2f b) {
        return new Vector2f(a.x * b.x, a.y * b.y);
    }

    public Vector2f crossMul(Vector2f a, Vector2f b) {
        return new Vector2f(a.x * b.y, a.y * b.x);
    }

    public static float dotProduct(Vector2f a, Vector2f b) {
        return (a.x * b.x + a.y * b.y);
    }

    public static Vector2f div(Vector2f a, Vector2f b) {
        return new Vector2f(a.x / b.x, a.y / b.y);
    }
}
