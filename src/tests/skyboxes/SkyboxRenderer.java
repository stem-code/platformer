package tests.skyboxes;

import engine.gfx.glwrapper.vertexobjects.VAO;
import engine.math.MathUtils;
import engine.math.Matrix4f;
import org.lwjgl.opengles.GLES20;

public class SkyboxRenderer {
    private static final float SIZE = 500.0f;

    private SkyboxShader shader;
    private float[] cubePositions = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };
    private VAO cube = new VAO();
    private Skybox skybox;
    private Skybox fadeSkybox = null;

    private float time = 0.0f;
    private float seconds = 1.0f;

    public SkyboxRenderer(SkyboxShader shader, Matrix4f projectionMatrix, Skybox skybox) {
        this.shader = shader;
        this.skybox = skybox;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
        cube.bind();
        cube.addAttribute(cubePositions, 0, 3);
        cube.unbind();
    }

    public void render() {
        shader.start();
        cube.enable();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(skybox.getType(), skybox.getID());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        if (fadeSkybox == null) {
            GLES20.glBindTexture(skybox.getType(), skybox.getID());
        } else {
            GLES20.glBindTexture(fadeSkybox.getType(), fadeSkybox.getID());
        }
        shader.loadBlendFactor(Math.min(time / seconds, 1.0f));
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0 , cubePositions.length / 3);
        cube.disable();
        shader.stop();
    }

    public void startFadeToSkybox(Skybox skybox, float seconds) {
        fadeSkybox = skybox;
        time = 0.0f;
        this.seconds = seconds;
    }

    public void updateFadeToSkybox(float delta) {
        time += delta;
        if (time > seconds) {
            time = 0.0f;
            if (!doneFading()) {
                skybox = fadeSkybox;
            }
            fadeSkybox = null;
        }
    }

    public boolean doneFading() {
        return fadeSkybox == null;
    }
}
