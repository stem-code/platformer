package tests;

import engine.core.State;
import engine.gfx.models.Model;
import engine.io.input.gfxrelated.OBJFile;
import engine.io.input.gfxrelated.PNGFile;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.ui.Keyboard;
import engine.ui.Mouse;
import org.lwjgl.Version;
import org.lwjgl.opengles.GLES20;
import tests.statics.StaticShader;
import tests.textures.Texture2D;

public class TestState extends State {
    private static final float MOVE_SPEED = 0.06f;
    private static final float SCALE_SPEED = 0.007f;

    private StaticShader shader;
    private Model model;
    private Texture2D texture;

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    private Vector2f lastMousePos;

    public TestState() {
        System.out.println("Hello World");
        System.out.println("LWJGL working on version " + Version.getVersion());

        shader = new StaticShader();
        model = new Model(new OBJFile("res/statics/dragon/dragonModel"));
        texture = new Texture2D(new PNGFile("res/statics/dragon/dragonTexture"));

        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1.0f, 1.0f, 1.0f);

        lastMousePos = new Vector2f();
    }

    @Override
    protected void mainThread(float delta) {
        if (Keyboard.isKeyPressed(Keyboard.KEY_Z)) {
            if (Keyboard.isKeyPressed(Keyboard.KEY_W)) {
                scale.z -= SCALE_SPEED * delta;
            } else if (Keyboard.isKeyPressed(Keyboard.KEY_S)) {
                scale.z += SCALE_SPEED * delta;
            }
            if (Keyboard.isKeyPressed(Keyboard.KEY_A)) {
                scale.x -= SCALE_SPEED * delta;
            } else if (Keyboard.isKeyPressed(Keyboard.KEY_D)) {
                scale.x += SCALE_SPEED * delta;
            }
            if (Keyboard.isKeyPressed(Keyboard.KEY_LEFT_SHIFT)) {
                scale.y -= SCALE_SPEED * delta;
            } else if (Keyboard.isKeyPressed(Keyboard.KEY_SPACE)) {
                scale.y += SCALE_SPEED * delta;
            }
        } else {
            if (Keyboard.isKeyPressed(Keyboard.KEY_W)) {
                position.z -= MOVE_SPEED * delta;
            } else if (Keyboard.isKeyPressed(Keyboard.KEY_S)) {
                position.z += MOVE_SPEED * delta;
            }
            if (Keyboard.isKeyPressed(Keyboard.KEY_A)) {
                position.x -= MOVE_SPEED * delta;
            } else if (Keyboard.isKeyPressed(Keyboard.KEY_D)) {
                position.x += MOVE_SPEED * delta;
            }
            if (Keyboard.isKeyPressed(Keyboard.KEY_LEFT_SHIFT)) {
                position.y -= MOVE_SPEED * delta;
            } else if (Keyboard.isKeyPressed(Keyboard.KEY_SPACE)) {
                position.y += MOVE_SPEED * delta;
            }
        }
        if (Mouse.isButtonDown(Mouse.BUTTON_LEFT)) {
            rotation.y += (Mouse.getCursorX() - lastMousePos.x) / 100.0f;
            rotation.x += (Mouse.getCursorY() - lastMousePos.y) / 100.0f;
        }
        lastMousePos.set(Mouse.getCursorX(), Mouse.getCursorY());

        GLES20.glClearColor(0.5f, 0.75f, 0.5f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);

        shader.start();

        //shader.loadViewMatrix(new Matrix4f(position.clone().neg().sub(0, 5, 30), new Vector3f()));
        //shader.loadLightPosition(position.clone().add(0, 5, 30));
        //shader.loadLightColor(new Vector3f(1.0f, 1.0f, 1.0f));
        //shader.loadShineDamper(10f);
        //shader.loadReflectivity(1f);

        model.enable();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(texture.getType(), texture.getID());
        shader.loadTransformationMatrix(new Matrix4f(position, rotation, scale));
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getVertexNum(), GLES20.GL_UNSIGNED_INT, 0);
        shader.loadTransformationMatrix(new Matrix4f());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getVertexNum(), GLES20.GL_UNSIGNED_INT, 0);
        model.disable();
        shader.stop();
    }

    @Override
    protected void onResize(int width, int height) {
        shader.start();
        //shader.resize();
        shader.stop();
    }

    @Override
    protected void onDestroy() {
        shader.delete();
        model.delete();
        texture.delete();
    }
}
