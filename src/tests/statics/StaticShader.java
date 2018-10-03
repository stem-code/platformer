package tests.statics;

import engine.gfx.glwrapper.shaders.*;
import engine.io.input.MyFile;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import tests.entities.Camera;
import tests.entities.Light;

public class StaticShader extends ShaderProgram {
    private UniformMat4 transformationMatrix;
    private UniformMat4 viewMatrix;
    private UniformMat4 projectionMatrix;

    private UniformVec3 lightPosition;
    private UniformVec3 lightColor;
    private UniformFloat shineDamper;
    private UniformFloat reflectivity;

    private UniformBoolean useFakeLighting;

    private UniformVec3 fogColor;

    public StaticShader() {
        super(new MyFile("src/tests/statics/vertexShader", ".txt"),
                new MyFile("src/tests/statics/fragmentShader", ".txt"),
                "coordinate", "textureCoord", "normal");
        transformationMatrix = new UniformMat4("transformationMatrix");
        viewMatrix = new UniformMat4("viewMatrix");
        projectionMatrix = new UniformMat4("projectionMatrix");
        lightPosition = new UniformVec3("lightPosition");
        lightColor = new UniformVec3("lightColor");
        shineDamper = new UniformFloat("shineDamper");
        reflectivity = new UniformFloat("reflectivity");
        useFakeLighting = new UniformBoolean("useFakeLighting");
        fogColor = new UniformVec3("fogColor");
        this.start();
        this.addUniform(transformationMatrix);
        this.addUniform(viewMatrix);
        this.addUniform(projectionMatrix);
        this.addUniform(lightPosition);
        this.addUniform(lightColor);
        this.addUniform(shineDamper);
        this.addUniform(reflectivity);
        this.addUniform(useFakeLighting);
        this.addUniform(fogColor);
        this.stop();
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadVariable(matrix.toArray());
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();
        this.viewMatrix.loadVariable(viewMatrix.toArray());
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.loadVariable(matrix.toArray());
    }

    public void loadLight(Light light) {
        lightPosition.loadVariable(light.getPosition());
        lightColor.loadVariable(light.getColor());
    }

    public void loadShineVariables(float reflectivity, float damper) {
        shineDamper.loadVariable(damper);
        this.reflectivity.loadVariable(reflectivity);
    }

    public void loadUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting.loadVariable(useFakeLighting);
    }

    public void loadFogColor(Vector3f fogColor) {
        this.fogColor.loadVariable(fogColor);
    }
}
