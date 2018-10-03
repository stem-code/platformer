package tests.skyboxes;

import engine.gfx.glwrapper.textures.Texture;
import engine.io.input.gfxrelated.PNGFile;
import org.lwjgl.opengles.GLES20;

public class Skybox extends Texture {
    public Skybox(String folderPath) {
        super(GLES20.GL_TEXTURE_CUBE_MAP);
        PNGFile[] pngFiles = {
                new PNGFile(folderPath + "/right"),
                new PNGFile(folderPath + "/left"),
                new PNGFile(folderPath + "/top"),
                new PNGFile(folderPath + "/bottom"),
                new PNGFile(folderPath + "/back"),
                new PNGFile(folderPath + "/front"),
        };
        this.bind();
        for (int i = 0;i < pngFiles.length;i++) {
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GLES20.GL_RGBA, pngFiles[i].getWidth(), pngFiles[i].getHeight(), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pngFiles[i].getData());
        }
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        this.unbind();
    }
}
