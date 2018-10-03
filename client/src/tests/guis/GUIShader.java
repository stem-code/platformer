package tests.guis;

import engine.gfx.glwrapper.shaders.ShaderProgram;
import engine.gfx.glwrapper.shaders.UniformMat4;
import engine.io.input.MyFile;
import engine.math.Matrix4f;

public class GUIShader extends ShaderProgram {
    private UniformMat4 transformationMatrix;

    public GUIShader() {
        super(new MyFile("src/tests/guis/vertexShader", ".txt"),
                new MyFile("src/tests/guis/fragmentShader", ".txt"),
                "coordinate", "textureCoord");
        transformationMatrix = new UniformMat4("transformationMatrix");
        this.start();
        this.addUniform(transformationMatrix);
        this.stop();
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadVariable(matrix.toArray());
    }
}
