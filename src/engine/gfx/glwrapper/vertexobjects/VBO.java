package engine.gfx.glwrapper.vertexobjects;

import engine.gfx.glwrapper.GLObject;
import org.lwjgl.opengles.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Deven on 3/4/2018.
 * Last edited on 4/18/18.
 */
// CREATES A VERTEX BUFFER OBJECT OF ANY TYPE AND ALLOW IT TO BE EASILY FILLED WITH DATA
public class VBO extends GLObject {
    public static final int BYTES_PER_SHORT = 2;
    public static final int BYTES_PER_INT = 4;
    public static final int BYTES_PER_FLOAT = 4;

    private int ID;
    private final int type;

    public VBO(int type) {
        ID = GLES20.glGenBuffers();
        this.type = type;
    }

    public void bind() {
        GLES20.glBindBuffer(type, ID);
    }

    public void unbind() {
        GLES20.glBindBuffer(type, 0);
    }

    public void storeData(short[] data) {
        ShortBuffer buffer = ByteBuffer.allocateDirect(data.length * BYTES_PER_SHORT).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(data).flip();
        GLES20.glBufferData(type, buffer, GLES20.GL_STATIC_DRAW);
    }

    public void storeData(int[] data) {
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length * BYTES_PER_INT).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data).flip();
        GLES20.glBufferData(type, buffer, GLES20.GL_STATIC_DRAW);
    }

    public void storeData(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data).flip();
        GLES20.glBufferData(type, buffer, GLES20.GL_STATIC_DRAW);
    }

    public void createEmpty(int numBytes) {
        GLES20.glBufferData(type, numBytes, GLES20.GL_STREAM_DRAW);
    }

    @Override
    public void deleteFromGL() {
        GLES20.glDeleteBuffers(ID);
    }
}
