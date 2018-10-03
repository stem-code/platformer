package engine.gfx.glwrapper.vertexobjects;

import engine.gfx.glwrapper.GLObject;
import org.lwjgl.opengles.GLES20;
import org.lwjgl.opengles.GLES30;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deven on 3/4/2018.
 * Last edited on 4/18/18.
 */
// CREATES A VERTEX ARRAY OBJECT AND PROVIDES UTILS TO FILL IT WITH DATA
public class VAO extends GLObject {
    private int ID;
    private int vertexNum;
    private List<Integer> attributes;
    private List<VBO> vbos;

    public VAO() {
        ID = GLES30.glGenVertexArrays();
        attributes = new ArrayList<>();
        vbos = new ArrayList<>();
    }

    public void bind() {
        GLES30.glBindVertexArray(ID);
    }

    public void unbind() {
        GLES30.glBindVertexArray(0);
    }

    public void enable() {
        bind();
        for (int i : attributes) {
            GLES20.glEnableVertexAttribArray(i);
        }
    }

    public void disable() {
        for (int i : attributes) {
            GLES20.glDisableVertexAttribArray(i);
        }
        unbind();
    }

    public void addIndices(int[] indices) {
        VBO vbo = new VBO(GLES20.GL_ELEMENT_ARRAY_BUFFER);
        vbo.bind();
        vbo.storeData(indices);
        vertexNum = indices.length;
        vbos.add(vbo);
    }

    public void addAttribute(float[] data, int attributeNumber, int variablesPerVertex) {
        VBO vbo = new VBO(GLES20.GL_ARRAY_BUFFER);
        vbo.bind();
        vbo.storeData(data);
        GLES20.glVertexAttribPointer(attributeNumber, variablesPerVertex, GLES20.GL_FLOAT, false, 0, 0);
        vbo.unbind();
        attributes.add(attributeNumber);
        vbos.add(vbo);
    }

    public void addAttribute(float[] data, int attributeNumber, int variablesPerVertex, boolean normalized) {
        VBO vbo = new VBO(GLES20.GL_ARRAY_BUFFER);
        vbo.bind();
        vbo.storeData(data);
        GLES20.glVertexAttribPointer(attributeNumber, variablesPerVertex, GLES20.GL_FLOAT, normalized, 0, 0);
        vbo.unbind();
        attributes.add(attributeNumber);
        vbos.add(vbo);
    }

    public void addAttribute(int[] data, int attributeNumber, int variablesPerVertex) {
        VBO vbo = new VBO(GLES20.GL_ARRAY_BUFFER);
        vbo.bind();
        vbo.storeData(data);
        GLES30.glVertexAttribIPointer(attributeNumber, variablesPerVertex, GLES20.GL_INT, 0, 0);
        vbo.unbind();
        attributes.add(attributeNumber);
        vbos.add(vbo);
    }

    public void addInstancedAttribute() {

    }

    @Override
    public void deleteFromGL() {
        GLES30.glDeleteVertexArrays(ID);
        for (VBO vbo : vbos) {
            vbo.delete();
        }
    }

    public int getID() {
        return ID;
    }

    public int getVertexNum() {
        return vertexNum;
    }
}
