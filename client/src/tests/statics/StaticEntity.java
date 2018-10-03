package tests.statics;

import engine.math.Vector3f;
import tests.models.SkinnedModel;

public class StaticEntity {
    private SkinnedModel model;
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public StaticEntity(SkinnedModel model) {
        this.model = model;
        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    public StaticEntity(SkinnedModel model, Vector3f position, Vector3f rotation) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        scale = new Vector3f(1.0f, 1.0f,1.0f);
    }

    public StaticEntity(SkinnedModel model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = new Vector3f(scale, scale, scale);
    }

    public StaticEntity(SkinnedModel model, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public SkinnedModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }
}