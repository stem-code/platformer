package engine.gfx.glwrapper.shaders;

import org.lwjgl.opengles.GLES20;

public class UniformInt extends Uniform {
    public UniformInt(String uniformName) {
        super(uniformName);
    }

    public void loadVariable(int variable) {
        GLES20.glUniform1i(getUniformLocation(), variable);
    }
}
