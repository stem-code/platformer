package engine.math;

public class Matrix4f {
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Matrix4f() {
        loadIdentity();
    }

    public Matrix4f(float x, float y, float z, float rx, float ry, float rz, float sx, float sy, float sz) {
        loadIdentity();
        translate(x, y, z);
        rotate(rx, ry, rz);
        scale(sx, sy, sz);
    }

    public Matrix4f(float x, float y, float z, float rx, float ry, float rz) {
        loadIdentity();
        translate(x, y, z);
        rotate(rx, ry, rz);
    }

    public Matrix4f(Vector3f translation, Vector3f rotation, Vector3f scale) {
        loadIdentity();
        translate(translation);
        rotate(rotation);
        scale(scale);
    }

    public Matrix4f(Vector3f translation, Vector3f rotation) {
        loadIdentity();
        translate(translation);
        rotate(rotation);
    }

    private void loadIdentity() {
        m00 = 1.0f;
        m11 = 1.0f;
        m22 = 1.0f;
        m33 = 1.0f;

        m01 = 0.0f;
        m02 = 0.0f;
        m03 = 0.0f;
        m10 = 0.0f;
        m12 = 0.0f;
        m13 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
    }

    public Vector4f multiply(Vector4f v) {
        return new Vector4f(
                m00 * v.x + m10 * v.y + m20 * v.z + m30 * v.w,
                m01 * v.x + m11 * v.y + m21 * v.z + m31 * v.w,
                m02 * v.x + m12 * v.y + m22 * v.z + m32 * v.w,
                m03 * v.x + m13 * v.y + m23 * v.z + m33 * v.w);
    }

    public Matrix4f multiply(Matrix4f m) {
        m00 = m00 * m.m00 + m10 * m.m01 + m20 * m.m02 + m30 * m.m03;
        m01 = m01 * m.m00 + m11 * m.m01 + m21 * m.m02 + m31 * m.m03;
        m02 = m02 * m.m00 + m12 * m.m01 + m22 * m.m02 + m32 * m.m03;
        m03 = m03 * m.m00 + m13 * m.m11 + m23 * m.m02 + m33 * m.m03;
        m10 = m00 * m.m10 + m10 * m.m11 + m20 * m.m12 + m30 * m.m13;
        m11 = m01 * m.m10 + m11 * m.m11 + m21 * m.m12 + m31 * m.m13;
        m12 = m02 * m.m10 + m12 * m.m11 + m22 * m.m12 + m32 * m.m13;
        m13 = m03 * m.m10 + m13 * m.m11 + m23 * m.m12 + m33 * m.m13;
        m20 = m00 * m.m20 + m10 * m.m21 + m20 * m.m22 + m30 * m.m23;
        m21 = m01 * m.m20 + m11 * m.m21 + m21 * m.m22 + m31 * m.m23;
        m22 = m02 * m.m20 + m12 * m.m21 + m22 * m.m22 + m32 * m.m23;
        m23 = m03 * m.m20 + m13 * m.m21 + m23 * m.m22 + m33 * m.m23;
        m30 = m00 * m.m30 + m10 * m.m31 + m20 * m.m32 + m30 * m.m33;
        m31 = m01 * m.m30 + m11 * m.m31 + m21 * m.m32 + m31 * m.m33;
        m32 = m02 * m.m30 + m12 * m.m31 + m22 * m.m32 + m32 * m.m33;
        m33 = m03 * m.m30 + m13 * m.m31 + m23 * m.m32 + m33 * m.m33;
        return this;
    }

    public void transpose(Matrix4f m) {
        m00 = m.m00;
        m01 = m.m10;
        m02 = m.m20;
        m10 = m.m01;
        m11 = m.m11;
        m12 = m.m21;
        m20 = m.m02;
        m21 = m.m12;
        m22 = m.m22;
    }

    public void translate(float x, float y, float z) {
        m30 += m00 * x + m10 * y + m20 * z;
        m31 += m01 * x + m11 * y + m21 * z;
        m32 += m02 * x + m12 * y + m22* z;
        m33 += m03 * x + m13 * y + m23 * z;
    }

    public void translate(Vector3f translation) {
        translate(translation.x, translation.y, translation.z);
    }

    // TODO: LEARN HOW ROTATION MATRIX MATHS WORKS
    public void rotate(float rotation, float x, float y, float z) {
        float c = (float) Math.cos(rotation);
        float s = (float) Math.sin(rotation);
        float oneminusc = 1.0f - c;
        float xy = x*y;
        float yz = y*z;
        float xz = x*z;
        float xs = x*s;
        float ys = y*s;
        float zs = z*s;

        float f00 = x*x*oneminusc+c;
        float f01 = xy*oneminusc+zs;
        float f02 = xz*oneminusc-ys;
        // n[3] not used
        float f10 = xy*oneminusc-zs;
        float f11 = y*y*oneminusc+c;
        float f12 = yz*oneminusc+xs;
        // n[7] not used
        float f20 = xz*oneminusc+ys;
        float f21 = yz*oneminusc-xs;
        float f22 = z*z*oneminusc+c;

        float t00 = m00 * f00 + m10 * f01 + m20 * f02;
        float t01 = m01 * f00 + m11 * f01 + m21 * f02;
        float t02 = m02 * f00 + m12 * f01 + m22 * f02;
        float t03 = m03 * f00 + m13 * f01 + m23 * f02;
        float t10 = m00 * f10 + m10 * f11 + m20 * f12;
        float t11 = m01 * f10 + m11 * f11 + m21 * f12;
        float t12 = m02 * f10 + m12 * f11 + m22 * f12;
        float t13 = m03 * f10 + m13 * f11 + m23 * f12;
        m20 = m00 * f20 + m10 * f21 + m20 * f22;
        m21 = m01 * f20 + m11 * f21 + m21 * f22;
        m22 = m02 * f20 + m12 * f21 + m22 * f22;
        m23 = m03 * f20 + m13 * f21 + m23 * f22;
        m00 = t00;
        m01 = t01;
        m02 = t02;
        m03 = t03;
        m10 = t10;
        m11 = t11;
        m12 = t12;
        m13 = t13;
    }

    public void rotate(float x, float y, float z) {
        rotate(x, 1.0f, 0.0f, 0.0f);
        rotate(y, 0.0f, 1.0f, 0.0f);
        rotate(z, 0.0f, 0.0f, 1.0f);
    }

    public void rotate(Vector3f rotation) {
        rotate(rotation.x, rotation.y, rotation.z);
    }

    public void scale(float size) {
        scale(size, size, size);
    }

    public void scale(float x, float y, float z) {
        m00 *= x;
        m01 *= x;
        m02 *= x;
        m03 *= x;
        m10 *= y;
        m11 *= y;
        m12 *= y;
        m13 *= y;
        m20 *= z;
        m21 *= z;
        m22 *= z;
        m23 *= z;
    }

    public void scale(Vector3f scale) {
        scale(scale.x, scale.y, scale.z);
    }

    public float[] toArray() {
        return new float[]{
                m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33};
    }

    public static Matrix4f get2DProjectionMatrix(int width, int height) {
        return new Matrix4f(new Vector3f(), new Vector3f(), new Vector3f(height / (float) width, 1.0f, 1.0f));
    }

    /**
     * @param FOV (The field of view.)
     * @param nearPlane (The closest that something can be and still be seen.)
     * @param farPlane (The furthest that something can be and still be seen.)
     * @return projectionMatrix (A matrix that will create a depth effect and fix the aspect ratio.)
     */
    public static Matrix4f get3DProjectionMatrix(int width, int height, float FOV, float nearPlane, float farPlane) {
        float aspectRatio = width/ (float) height;
        float xScale = (1.0f / (float) Math.tan(Math.toRadians(FOV / 2.0f)));
        float yScale = xScale * aspectRatio;
        float frustumLength = farPlane - nearPlane;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
        projectionMatrix.m22 = -((farPlane + nearPlane) / frustumLength);
        projectionMatrix.m23 = -1.0f;
        projectionMatrix.m32 = -((2.0f * nearPlane * farPlane) / frustumLength);
        projectionMatrix.m33 = 0.0f;
        return projectionMatrix;
    }
}
