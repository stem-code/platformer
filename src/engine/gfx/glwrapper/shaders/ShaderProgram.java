package engine.gfx.glwrapper.shaders;

import engine.gfx.glwrapper.GLObject;
import engine.io.input.MyFile;
import org.lwjgl.opengles.GLES20;

/**
 * Created by Deven on 3/4/2018.
 * Last edited on 4/18/18.
 */

public abstract class ShaderProgram extends GLObject {
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public ShaderProgram(MyFile vertexShaderCode, MyFile fragmentShaderCode, String... attributeNames) {
        programID = GLES20.glCreateProgram();
        if (programID == 0) {
            return;
        }
        vertexShaderID = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode.getAsText());
        fragmentShaderID = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode.getAsText());
        GLES20.glAttachShader(programID, vertexShaderID);
        GLES20.glAttachShader(programID, fragmentShaderID);
        bindAttributeLocations(attributeNames);
        GLES20.glLinkProgram(programID);
        int linked = GLES20.glGetProgrami(programID, GLES20.GL_LINK_STATUS);
        if (linked == GLES20.GL_FALSE) {
            System.err.println("OpenGL ES Shader Error: " + GLES20.glGetProgramInfoLog(programID));
            delete();
        }
    }

    public void addUniform(Uniform uniform) {
        uniform.getUniformLocationFromShader(programID);
    }

    public void start() {
        GLES20.glUseProgram(programID);
    }

    public void stop() {
        GLES20.glUseProgram(0);
    }

    private void bindAttributeLocations(String... attributeNames) {
        for (int i = 0;i < attributeNames.length;i++) {
            GLES20.glBindAttribLocation(programID, i, attributeNames[i]);
        }
    }

    private int loadShader(int shaderType, String shaderCode) {
        int shaderID = GLES20.glCreateShader(shaderType);
        if (shaderID == 0) {
            return 0;
        }
        GLES20.glShaderSource(shaderID, shaderCode);
        GLES20.glCompileShader(shaderID);
        int compiled = GLES20.glGetShaderi(shaderID, GLES20.GL_COMPILE_STATUS);
        if (compiled == GLES20.GL_FALSE) {
            System.err.println("OpenGL ES Shader Error: " + GLES20.glGetShaderInfoLog(shaderID));
            GLES20.glDeleteShader(shaderID);
            return 0;
        }
        return shaderID;
    }

    @Override
    public void deleteFromGL() {
        GLES20.glDetachShader(programID, vertexShaderID);
        GLES20.glDetachShader(programID, fragmentShaderID);
        GLES20.glDeleteShader(vertexShaderID);
        GLES20.glDeleteShader(fragmentShaderID);
        GLES20.glDeleteProgram(programID);
    }
}
