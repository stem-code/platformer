package tests.particles;

import engine.math.Matrix4f;
import tests.entities.Camera;

import java.util.*;

public class ParticleMaster {
    private static List<Particle> particles = new ArrayList<>();
    private static ParticleRenderer renderer;

    public static void init(Matrix4f projectionMatrix) {
        renderer = new ParticleRenderer(projectionMatrix);
    }

    public static void update(float delta) {
        for (int i = particles.size() - 1;i >= 0;i--) {
            Particle particle = particles.get(i);
            particle.update(delta);
            if (!particle.isAlive()) {
                particles.remove(i);
            }
        }
    }

    public static void renderParticles(Camera camera) {
        renderer.render(particles, camera);
    }

    protected static void addParticle(Particle particle) {
        particles.add(particle);
    }
}
