package tests.skyboxes;

import engine.gfx.glwrapper.shaders.*;
import engine.io.input.MyFile;
import engine.math.MathUtils;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import tests.entities.Camera;

public class SkyboxShader extends ShaderProgram {
    private static final float ROTATION_SPEED = 1.0f;

    private UniformMat4 viewMatrix;
    private UniformMat4 projectionMatrix;
    private UniformVec3 fogColor;
    private UniformInt cubeMap;
    private UniformInt fadeCubeMap;
    private UniformFloat blendFactor;

    private float rotation = 0.0f;

    public SkyboxShader() {
        super(new MyFile("src/tests/skyboxes/vertexShader", ".txt"),
                new MyFile("src/tests/skyboxes/fragmentShader", ".txt"),
                "coordinate");
        viewMatrix = new UniformMat4("viewMatrix");
        projectionMatrix = new UniformMat4("projectionMatrix");
        fogColor = new UniformVec3("fogColor");
        cubeMap = new UniformInt("cubeMap");
        fadeCubeMap = new UniformInt("fadeCubeMap");
        blendFactor = new UniformFloat("blendFactor");
        this.start();
        this.addUniform(viewMatrix);
        this.addUniform(projectionMatrix);
        this.addUniform(fogColor);
        this.addUniform(cubeMap);
        this.addUniform(fadeCubeMap);
        this.addUniform(blendFactor);
        this.stop();
    }

    public void rotateSkybox(float delta) {
        rotation += ROTATION_SPEED * delta;
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();
        viewMatrix.m30 = 0.0f;
        viewMatrix.m31 = 0.0f;
        viewMatrix.m32 = 0.0f;
        viewMatrix.rotate(MathUtils.toRadians(rotation), 0.0f, 1.0f, 0.0f);
        this.viewMatrix.loadVariable(viewMatrix.toArray());
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.loadVariable(matrix.toArray());
    }

    public void loadFogColor(Vector3f fogColor) {
        this.fogColor.loadVariable(fogColor);
    }

    public void connectTextureUnits() {
        cubeMap.loadVariable(0);
        fadeCubeMap.loadVariable(1);
    }

    public void loadBlendFactor(float blendFactor) {
        this.blendFactor.loadVariable(blendFactor);
    }
}
