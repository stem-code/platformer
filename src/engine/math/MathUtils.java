package engine.math;

/**
 * Created by Deven on 3/3/2018.
 * Last edited on 4/18/18.
 */

public class MathUtils {
    public static final float PI = (float) Math.PI;

    public static float random() {
        return (float) Math.random();
    }

    public static float pow(float base, float exp) {
        return (float) Math.pow(base, exp);
    }

    public static float log(float value) {
        return (float) Math.log(value);
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    public static float floor(float value) {
        return (float) Math.floor(value);
    }

    public static float toDegrees(float radianVal) {
        return (float) Math.toDegrees(radianVal);
    }

    public static float toRadians(float degreeVal) {
        return (float) Math.toRadians(degreeVal);
    }

    public static float sin(float theta) {
        return (float) Math.sin(theta);
    }

    public static float cos(float theta) {
        return (float) Math.cos(theta);
    }

    public static float tan(float theta) {
        return (float) Math.tan(theta);
    }

    /*public static Vector2f calculateOrbitCoordinates(float radius, float theta) {
        return new Vector2f(((float) Math.cos(theta)) * radius, ((float) Math.sin(theta)) * radius);
    }

    public static Vector2f calculateOrbitCoordinates(float radius, float theta, Vector2f vertex) {
        return new Vector2f((((float) Math.cos(theta)) * radius) + vertex.x, (((float) Math.sin(theta)) * radius) + vertex.y);
    }*/
}
