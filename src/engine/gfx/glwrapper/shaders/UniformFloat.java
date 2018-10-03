package engine.gfx.glwrapper.shaders;

import org.lwjgl.opengles.GLES20;

public class UniformFloat extends Uniform {
    public UniformFloat(String uniformName) {
        super(uniformName);
    }

    public void loadVariable(float variable) {
        GLES20.glUniform1f(getUniformLocation(), variable);
    }
}
