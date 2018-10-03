package engine.gfx.glwrapper.textures;

import engine.gfx.glwrapper.GLObject;
import org.lwjgl.opengles.GLES20;

/**
 * Created by Deven on 3/3/2018.
 * Last edited on 4/18/18.
 */
// CREATES A TEXTURE OF ANY TYPE
public abstract class Texture extends GLObject {
    private int ID;
    private final int type;

    public Texture(int type) {
        ID = GLES20.glGenTextures();
        this.type = type;
    }

    protected void bind() {
        GLES20.glBindTexture(type, ID);
    }

    protected void unbind() {
        GLES20.glBindTexture(type, 0);
    }

    @Override
    public void deleteFromGL() {
        GLES20.glDeleteTextures(ID);
    }

    public int getType() {
        return type;
    }

    public int getID() {
        return ID;
    }
}
