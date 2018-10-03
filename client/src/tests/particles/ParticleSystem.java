package tests.particles;

import engine.math.MathUtils;
import engine.math.Vector3f;

public class ParticleSystem {
    private float pps;
    private float speed;
    private float gravity;
    private float lifeLength;

    public ParticleSystem(float pps, float speed, float gravity, float lifeLength) {
        this.pps = pps;
        this.speed = speed;
        this.gravity = gravity;
        this.lifeLength = lifeLength;
    }

    public void generateParticles(float delta, Vector3f emitPoint) {
        float particlesThisFrame = pps * delta;
        int wholeParticles = (int) Math.floor(particlesThisFrame);
        float partialParticle = particlesThisFrame % 1.0f;
        for (int i = 0;i < wholeParticles;i++) {
            generateParticle(emitPoint);
        }
        if (partialParticle > MathUtils.random()) {
            generateParticle(emitPoint);
        }
    }

    private void generateParticle(Vector3f emitPoint) {
        float theta = MathUtils.toRadians(MathUtils.random() * 360.0f);
        float xDirection = MathUtils.sin(theta);
        float zDirection = MathUtils.cos(theta);
        Vector3f velocity = new Vector3f(xDirection, 1.0f, zDirection);
        velocity.normalize();
        velocity.mul(speed);
        new Particle(emitPoint.clone(), 0.0f, 1.0f, velocity, gravity, lifeLength);
    }
}
