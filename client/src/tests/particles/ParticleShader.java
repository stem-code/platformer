package tests.particles;

import engine.gfx.glwrapper.shaders.ShaderProgram;
import engine.gfx.glwrapper.shaders.UniformMat4;
import engine.io.input.MyFile;
import engine.math.Matrix4f;

public class ParticleShader extends ShaderProgram {
    private UniformMat4 projectionMatrix;
    private UniformMat4 viewMatrix;
    private UniformMat4 transformationMatrix;

    public ParticleShader() {
        super(new MyFile("src/tests/particles/vertexShader", ".txt"),
                new MyFile("src/tests/particles/fragmentShader", ".txt"),
                "coordinate");
        projectionMatrix = new UniformMat4("projectionMatrix");
        viewMatrix = new UniformMat4("viewMatrix");
        transformationMatrix = new UniformMat4("transformationMatrix");
        this.start();
        this.addUniform(projectionMatrix);
        this.addUniform(viewMatrix);
        this.addUniform(transformationMatrix);
        this.stop();
    }

    public void loadProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix.loadVariable(projectionMatrix.toArray());
    }

    public void loadModelViewMatrix(Matrix4f transformationMatrix, Matrix4f viewMatrix) {
        this.viewMatrix.loadVariable(viewMatrix.toArray());
        this.transformationMatrix.loadVariable(transformationMatrix.toArray());
    }
}
