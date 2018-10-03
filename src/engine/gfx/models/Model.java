package engine.gfx.models;

import engine.gfx.glwrapper.vertexobjects.VAO;
import engine.io.input.gfxrelated.OBJFile;

public class Model extends VAO {
    public Model(OBJFile objFile) {
        this(objFile.getVerticesArray(), objFile.getTextureCoordsArray(), objFile.getNormalsArray(), objFile.getIndicesArray());
    }

    public Model(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
        super();
        this.bind();
        this.addIndices(indices);
        this.addAttribute(vertices, 0, 3);
        this.addAttribute(textureCoords, 1, 2);
        this.addAttribute(normals, 2, 3);
        this.unbind();
    }
}
