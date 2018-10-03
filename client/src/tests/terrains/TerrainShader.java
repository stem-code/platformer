package tests.terrains;

import engine.gfx.glwrapper.shaders.*;
import engine.io.input.MyFile;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import tests.entities.Camera;
import tests.entities.Light;

public class TerrainShader extends ShaderProgram {
    private UniformMat4 transformationMatrix;
    private UniformMat4 viewMatrix;
    private UniformMat4 projectionMatrix;

    private UniformVec3 lightPosition;
    private UniformVec3 lightColor;
    private UniformFloat shineDamper;
    private UniformFloat reflectivity;

    private UniformVec3 fogColor;

    private UniformInt defaultTexture;
    private UniformInt rTexture;
    private UniformInt gTexture;
    private UniformInt bTexture;
    private UniformInt blendMap;

    public TerrainShader() {
        super(new MyFile("src/tests/terrains/vertexShader", ".txt"),
                new MyFile("src/tests/terrains/fragmentShader", ".txt"),
                "coordinate", "textureCoord", "normal");
        transformationMatrix = new UniformMat4("transformationMatrix");
        viewMatrix = new UniformMat4("viewMatrix");
        projectionMatrix = new UniformMat4("projectionMatrix");
        lightPosition = new UniformVec3("lightPosition");
        lightColor = new UniformVec3("lightColor");
        shineDamper = new UniformFloat("shineDamper");
        reflectivity = new UniformFloat("reflectivity");
        fogColor = new UniformVec3("fogColor");
        defaultTexture = new UniformInt("defaultTexture");
        rTexture = new UniformInt("rTexture");
        gTexture = new UniformInt("gTexture");
        bTexture = new UniformInt("bTexture");
        blendMap = new UniformInt("blendMap");
        this.start();
        this.addUniform(transformationMatrix);
        this.addUniform(viewMatrix);
        this.addUniform(projectionMatrix);
        this.addUniform(lightPosition);
        this.addUniform(lightColor);
        this.addUniform(shineDamper);
        this.addUniform(reflectivity);
        this.addUniform(fogColor);
        this.addUniform(defaultTexture);
        this.addUniform(rTexture);
        this.addUniform(gTexture);
        this.addUniform(bTexture);
        this.addUniform(blendMap);
        this.stop();
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadVariable(matrix.toArray());
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();
        this.viewMatrix.loadVariable(viewMatrix.toArray());
    }

    public  void loadProjectionMatrix(Matrix4f matrix) {
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

    public void loadFogColor(Vector3f fogColor) {
        this.fogColor.loadVariable(fogColor);
    }

    public void connectTextureUnits() {
        defaultTexture.loadVariable(0);
        rTexture.loadVariable(1);
        gTexture.loadVariable(2);
        bTexture.loadVariable(3);
        blendMap.loadVariable(4);
    }
}
