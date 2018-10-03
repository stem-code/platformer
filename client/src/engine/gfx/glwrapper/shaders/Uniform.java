package engine.gfx.glwrapper.shaders;

import org.lwjgl.opengles.GLES20;

/**
 * Created by Deven on 3/14/2018.
 * Last edited on 4/18/18.
 */

public abstract class Uniform {
    private String uniformName;
    private int uniformLocation;

    // TODO: GO OVER SHADER CLASSES TO ORGANIZE AND DOCUMENT
    public Uniform(String uniformName) {
        this.uniformName = uniformName;
    }

    protected void getUniformLocationFromShader(int programID) {
        uniformLocation = GLES20.glGetUniformLocation(programID, uniformName);
        if (uniformLocation == -1) {
            System.err.println("OpenGL ES Shader Error: Uniform location not found.");
        }
    }

    // TODO: ADD METHODS IN CHILD CLASSES TO LOAD VARIABLES (IF POSSIBLE ADD AN ABSTRACT METHOD TO ENSURE THIS)

    public String getUniformName() {
        return uniformName;
    }

    public int getUniformLocation() {
        return uniformLocation;
    }
}
