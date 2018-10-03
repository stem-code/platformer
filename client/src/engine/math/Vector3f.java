package engine.math;

/**
 * Created by Deven on 2/5/2018.
 * Last edited on 6/11/2018.
 */

public class Vector3f extends Vector2f {
    public float z;

    public Vector3f() {
        set(0, 0, 0);
    }

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3f(Vector3f v) {
        set(v);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3f v) {
        set(v.x, v.y, v.z);
    }

    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3f add(Vector3f v) {
        return add(v.x, v.y, v.z);
    }

    public Vector3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3f sub(Vector3f v) {
        return sub(v.x, v.y, v.z);
    }

    @Override
    public Vector3f mul(float f) {
        return mul(f, f, f);
    }

    public Vector3f mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vector3f mul(Vector3f v) {
        return mul(v.x, v.y, v.z);
    }

    public float dotProduct(Vector3f v) {
        return (x * v.x) + (y * v.y) + (z * v.z);
    }

    @Override
    public Vector3f div(float f) {
        return div(f, f, f);
    }

    public Vector3f div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vector3f div(Vector3f v) {
        return div(v.x, v.y, v.z);
    }

    @Override
    public Vector3f neg() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public float dst(float x, float y, float z) {
        return MathUtils.sqrt(dstSq(x, y, z));
    }

    public float dst(Vector3f v) {
        return dst(v.x, v.y, v.z);
    }

    public float dstSq(float x, float y, float z) {
        return (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y) + (this.z - z) * (this.z - z);
    }

    public float dstSq(Vector3f v) {
        return dstSq(v.x, v.y, v.z);
    }

    @Override
    public float length() {
        return MathUtils.sqrt(lengthSq());
    }

    @Override
    public float lengthSq() {
        return (x * x) + (y * y) + (z * z);
    }

    @Override
    public void perp() {
        super.perp();
    }

    @Override
    public void normalize() {
        float lenSq = lengthSq();
        if (lenSq > 0.0001f * 0.0001f) {
            float invLen = 1.0f / (float) StrictMath.sqrt(lenSq);
            mul(invLen);
        }
    }

    @Override
    public Vector3f clone() {
        return new Vector3f(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    // STATIC METHODS
    public static Vector3f add(Vector3f a, Vector3f b) {
        return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vector3f sub(Vector3f a, Vector3f b) {
        return new Vector3f(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vector3f mul(Vector3f v, float f) {
        return new Vector3f(v.x * f, v.y * f, v.z * f);
    }

    public static Vector3f mul(Vector3f a, Vector3f b) {
        return new Vector3f(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public static float dotProduct(Vector3f a, Vector3f b) {
        return (a.x * b.x + a.y * b.y + a.z * b.z);
    }

    public static Vector3f div(Vector3f a, Vector3f b) {
        return new Vector3f(a.x / b.x, a.y / b.y, a.z / b.z);
    }
}
