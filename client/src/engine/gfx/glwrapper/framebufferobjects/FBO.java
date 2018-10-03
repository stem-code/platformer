package engine.gfx.glwrapper.framebufferobjects;

import engine.gfx.glwrapper.GLObject;
import org.lwjgl.opengles.GLES20;

public class FBO extends GLObject {
    private int ID;

    public FBO() {
        ID = GLES20.glGenFramebuffers();
        // TODO: FINISH CREATING FBO CLASS
    }

    @Override
    protected void deleteFromGL() {
        GLES20.glDeleteFramebuffers(ID);
    }
}
