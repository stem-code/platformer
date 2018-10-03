package tests.particles;

import engine.math.Vector3f;

public class Particle {
    private Vector3f position;
    private float rotation;
    private float scale;
    private Vector3f velocity;
    private float gravity;
    private float lifeLength;

    private float time = 0.0f;

    public Particle(Vector3f position, float rotation, float scale, Vector3f velocity, float gravity, float lifeLength) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.velocity = velocity;
        this.gravity = gravity;
        this.lifeLength = lifeLength;
        ParticleMaster.addParticle(this);
    }

    public void update(float delta) {
        velocity.y += gravity * delta;
        position.add(velocity.clone().mul(delta));
        time += delta;
    }

    public boolean isAlive() {
        if (time > lifeLength) {
            return false;
        }
        return true;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }
}
