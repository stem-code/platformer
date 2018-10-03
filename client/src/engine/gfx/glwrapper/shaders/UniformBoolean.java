package engine.gfx.glwrapper.shaders;

import org.lwjgl.opengles.GLES20;

/**
 * Created by Deven on 3/14/2018.
 * Last edited on 4/18/18.
 */

public class UniformBoolean extends Uniform {
    public UniformBoolean(String uniformName) {
        super(uniformName);
    }

    public void loadVariable(Boolean variable) {
        int temp = 0;
        if (variable) {
            temp = 1;
        }
        GLES20.glUniform1i(getUniformLocation(), temp);
    }
}
