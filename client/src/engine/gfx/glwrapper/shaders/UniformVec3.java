package engine.gfx.glwrapper.shaders;

import engine.math.Vector3f;
import org.lwjgl.opengles.GLES20;

/**
 * Created by Deven on 3/15/2018.
 * Last edited on 4/18/18.
 */

public class UniformVec3 extends Uniform {
    public UniformVec3(String uniformName) {
        super(uniformName);
    }

    public void loadVariable(Vector3f variable) {
        GLES20.glUniform3f(getUniformLocation(),  variable.x, variable.y, variable.z);
    }
}
