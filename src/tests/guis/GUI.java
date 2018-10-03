package tests.guis;

import engine.gfx.glwrapper.textures.Texture;
import engine.io.input.gfxrelated.PNGFile;
import engine.math.Vector2f;
import org.lwjgl.opengles.GLES20;

public class GUI extends Texture {
    private Vector2f position;
    private Vector2f scale;

    public GUI(PNGFile pngFile, Vector2f position, Vector2f scale) {
        super(GLES20.GL_TEXTURE_2D);
        this.position = position;
        this.scale = scale;
        this.bind();
        GLES20.glTexImage2D(getType(), 0, GLES20.GL_RGBA, pngFile.getWidth(), pngFile.getHeight(), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pngFile.getData());
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        this.unbind();
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
