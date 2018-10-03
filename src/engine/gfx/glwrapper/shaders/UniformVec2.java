package engine.gfx.glwrapper.shaders;

import engine.math.Vector2f;
import org.lwjgl.opengles.GLES20;

/**
 * Created by Deven on 3/21/2018.
 * Last edited on 4/18/18.
 */

public class UniformVec2 extends Uniform {
    public UniformVec2(String uniformName) {
        super(uniformName);
    }

    public void loadVariable(Vector2f variable) {
        GLES20.glUniform2f(getUniformLocation(), variable.x, variable.y);
    }
}
