package tests.terrains;

import engine.gfx.models.Model;
import tests.textures.TerrainTexture;
import tests.textures.TerrainTexturePack;

public class Terrain {
    private static final float SIZE = 800.0f;
    private static final int VERTEX_COUNT_PER_SIDE = 128;

    private float x;
    private float z;
    private Model model;
    private TerrainTexture blendMap;
    private TerrainTexturePack texturePack;

    public Terrain(float gridX, float gridZ, TerrainTexture blendMap, TerrainTexturePack texturePack) {
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.blendMap = blendMap;
        this.texturePack = texturePack;
        model = generateTerrain();
    }

    private Model generateTerrain() {
        int totalVertexCount = VERTEX_COUNT_PER_SIDE * VERTEX_COUNT_PER_SIDE;
        float[] vertices = new float[totalVertexCount * 3];
        float[] textureCoords = new float[totalVertexCount * 2];
        float[] normals = new float[totalVertexCount * 3];
        int[] indices = new int[6 * (VERTEX_COUNT_PER_SIDE - 1) * (VERTEX_COUNT_PER_SIDE - 1)];
        int currentVertex = 0;
        for (int i = 0;i < VERTEX_COUNT_PER_SIDE;i++) {
            for (int j = 0;j < VERTEX_COUNT_PER_SIDE;j++) {
                int indexForVec2 = currentVertex * 2;
                int indexForVec3 = currentVertex * 3;
                vertices[indexForVec3] = j * SIZE / (VERTEX_COUNT_PER_SIDE - 1);
                vertices[indexForVec3 + 1] = 0.0f;
                vertices[indexForVec3 + 2] = i * SIZE / (VERTEX_COUNT_PER_SIDE - 1);
                textureCoords[indexForVec2] = j / (float) (VERTEX_COUNT_PER_SIDE - 1);
                textureCoords[indexForVec2 + 1] = i / (float) (VERTEX_COUNT_PER_SIDE - 1);
                normals[indexForVec3] = 0.0f;
                normals[indexForVec3 + 1] = 1.0f;
                normals[indexForVec3 + 2] = 0.0f;
                currentVertex++;
            }
        }
        int currentSquare = 0;
        for (int i = 0;i < VERTEX_COUNT_PER_SIDE - 1;i++) {
            for (int j = 0;j < VERTEX_COUNT_PER_SIDE - 1;j++) {
                int topLeft = j + (i * VERTEX_COUNT_PER_SIDE);
                int topRight = topLeft + 1;
                int bottomLeft = topLeft + VERTEX_COUNT_PER_SIDE;
                int bottomRight = bottomLeft + 1;
                int indexForSquare = currentSquare * 6;
                indices[indexForSquare] = topLeft;
                indices[indexForSquare + 1] = bottomLeft;
                indices[indexForSquare + 2] = topRight;
                indices[indexForSquare + 3] = topRight;
                indices[indexForSquare + 4] = bottomLeft;
                indices[indexForSquare + 5] = bottomRight;
                currentSquare++;
            }
        }
        return new Model(vertices, textureCoords, normals, indices);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public Model getModel() {
        return model;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }
}