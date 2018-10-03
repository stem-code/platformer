package tests.textures;

import engine.gfx.glwrapper.textures.Texture;
import engine.io.input.gfxrelated.PNGFile;
import org.lwjgl.opengles.GLES20;

public class Texture2D extends Texture {

    public Texture2D(PNGFile pngFile) {
        super(GLES20.GL_TEXTURE_2D);
        this.bind();

        GLES20.glTexImage2D(getType(), 0, GLES20.GL_RGBA, pngFile.getWidth(), pngFile.getHeight(), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pngFile.getData());

        //GLES30.glGenerateMipmap(getType());
        //GLES30.glTexParameteri(getType(), GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
        //GLES30.glTexParameterf(getType(), GLES30.GL_MAX_TEXTURE_LOD_BIAS, -0.4f);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        this.unbind();
    }
}
