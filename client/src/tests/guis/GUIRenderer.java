package tests.guis;

import engine.gfx.GFXEngine;
import engine.gfx.glwrapper.vertexobjects.VAO;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import org.lwjgl.opengles.GLES20;

import java.util.List;

public class GUIRenderer {
    private static final float[] quadPositions = {
            -1, 1,
            -1, -1,
            1, 1,
            1, -1
    };
    private static final float[] quadTextureCoords = {
            0, 0,
            0, 1,
            1, 0,
            1, 1
    };
    private GUIShader shader = new GUIShader();
    private VAO quad = new VAO();

    public GUIRenderer() {
        quad.bind();
        quad.addAttribute(quadPositions, 0, 2);
        quad.addAttribute(quadTextureCoords, 1, 2);
        quad.unbind();
    }

    public void render(List<GUI> guis) {
        GFXEngine.disableCulling();
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        shader.start();
        quad.enable();
        for (GUI gui : guis) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(gui.getType(), gui.getID());
            shader.loadTransformationMatrix(new Matrix4f(new Vector3f(gui.getPosition().x, gui.getPosition().y, 0.0f), new Vector3f(), new Vector3f(new Vector3f(gui.getScale().x, gui.getScale().y, 1.0f))));
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, quadPositions.length / 2);
        }
        quad.disable();
        shader.stop();
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GFXEngine.enableCulling();
    }
}
