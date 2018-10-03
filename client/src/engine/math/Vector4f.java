package engine.math;

/**
 * Created by Deven on 6/11/2018.
 * Last edited on 6/11/2018.
 */

public class Vector4f extends Vector3f {
    public float w;

    public Vector4f() {
        set(0, 0, 0, 0);
    }

    public Vector4f(float x, float y, float z, float w) {
        set(x, y, z, w);
    }

    public Vector4f(Vector4f v) {
        set(v);
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(Vector4f v) {
        set(v.x, v.y, v.z, v.w);
    }

    public Vector4f add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public Vector4f add(Vector4f v) {
        return add(v.x, v.y, v.z, v.w);
    }

    public Vector4f sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    public Vector4f sub(Vector4f v) {
        return sub(v.x, v.y, v.z, v.w);
    }

    @Override
    public Vector4f mul(float f) {
        return mul(f, f, f, f);
    }

    public Vector4f mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }

    public Vector4f mul(Vector4f v) {
        return mul(v.x, v.y, v.z, v.w);
    }

    public float dotProduct(Vector4f v) {
        return (x * v.x) + (y * v.y) + (z * v.z) + (w * v.w);
    }

    @Override
    public Vector4f div(float f) {
        return div(f, f, f, f);
    }

    public Vector4f div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vector4f div(Vector4f v) {
        return div(v.x, v.y, v.z, v.w);
    }

    @Override
    public Vector4f neg() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public float dst(float x, float y, float z, float w) {
        return MathUtils.sqrt(dstSq(x, y, z, w));
    }

    public float dst(Vector4f v) {
        return dst(v.x, v.y, v.z, v.w);
    }

    public float dstSq(float x, float y, float z, float w) {
        return (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y) + (this.z - z) * (this.z - z) + (this.w - w) * (this.w - w);
    }

    public float dstSq(Vector4f v) {
        return dstSq(v.x, v.y, v.z, v.w);
    }

    @Override
    public float length() {
        return MathUtils.sqrt(lengthSq());
    }

    @Override
    public float lengthSq() {
        return (x * x) + (y * y) + (z * z) + (w * w);
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
    public Vector4f clone() {
        return new Vector4f(x, y, z, w);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    // STATIC METHODS
    public static Vector4f add(Vector4f a, Vector4f b) {
        return new Vector4f(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w);
    }

    public static Vector4f sub(Vector4f a, Vector4f b) {
        return new Vector4f(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w);
    }

    public static Vector4f mul(Vector4f a, float f) {
        return new Vector4f(a.x * f, a.y * f, a.z * f, a.w * f);
    }

    public static Vector4f mul(Vector4f a, Vector4f b) {
        return new Vector4f(a.x * b.x, a.y * b.y, a.z * b.z, a.w * b.w);
    }

    public static float dotProduct(Vector4f a, Vector4f b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    public static Vector4f div(Vector4f a, Vector4f b) {
        return new Vector4f(a.x / b.x, a.y / b.y, a.z / b.z, a.w / b.w);
    }
}
