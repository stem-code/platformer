package tests.entities;

import engine.entities.Entity;
import engine.math.Vector3f;

public class Light extends Entity {
    private Vector3f position;
    private Vector3f color;

    public Light() {
        position = new Vector3f();
        color = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }
}
