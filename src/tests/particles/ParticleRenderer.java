package tests.particles;

import engine.gfx.GFXEngine;
import engine.gfx.glwrapper.vertexobjects.VAO;
import engine.math.Matrix4f;
import org.lwjgl.opengles.GLES20;
import tests.entities.Camera;

import java.util.List;

public class ParticleRenderer {
    private static final float[] quadPositions = {
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, 0.5f,
            0.5f, -0.5f
    };
    private ParticleShader shader = new ParticleShader();
    private VAO quad = new VAO();

    public ParticleRenderer(Matrix4f projectionMatrix) {
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
        quad.bind();
        quad.addAttribute(quadPositions, 0, 2);
        quad.unbind();
    }

    public void render(List<Particle> particles, Camera camera) {
        shader.start();
        quad.enable();
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthMask(false);
        for (Particle particle : particles) {
            Matrix4f transformationMatrix = new Matrix4f();
            transformationMatrix.translate(particle.getPosition());
            Matrix4f viewMatrix = camera.getViewMatrix();
            transformationMatrix.transpose(viewMatrix);
            transformationMatrix.rotate(particle.getRotation(), 0.0f, 0.0f, 1.0f);
            transformationMatrix.scale(particle.getScale());
            shader.loadModelViewMatrix(transformationMatrix, viewMatrix);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, quadPositions.length / 2);
        }
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDepthMask(true);
        quad.disable();
        shader.stop();
    }
}
