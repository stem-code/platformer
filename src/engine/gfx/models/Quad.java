package engine.gfx.models;

import engine.gfx.glwrapper.vertexobjects.VAO;

public class Quad extends VAO {
    private final int[] indices = {
            0, 1, 3,
            3, 1, 2
    };
    private final float[] vertices = {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
    };
    private final float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    public Quad() {
        this.bind();
        this.addIndices(indices);
        this.addAttribute(vertices, 0, 3);
        this.addAttribute(textureCoords, 1, 2);
        this.unbind();
    }
}
