package engine.gfx.glwrapper.shaders;

import org.lwjgl.opengles.GLES20;

/**
 * Created by Deven on 3/21/2018.
 * Last edited on 4/18/18.
 */

public class UniformMat4 extends Uniform {
    public UniformMat4(String uniformName) {
        super(uniformName);
    }

    public void loadVariable(float[] variable) {
        GLES20.glUniformMatrix4fv(getUniformLocation(), false, variable);
    }
}
