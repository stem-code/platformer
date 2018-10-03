package tests.terrains;

import engine.math.Matrix4f;
import engine.math.Vector3f;
import org.lwjgl.opengles.GLES20;
import tests.textures.TerrainTexturePack;

import java.util.List;

public class TerrainRenderer {
    private TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    private void prepareTerrain(Terrain terrain) {
        terrain.getModel().enable();
        shader.loadShineVariables(0.0f, 1.0f);
        TerrainTexturePack texturePack = terrain.getTexturePack();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(texturePack.getDefaultTexture().getType(), texturePack.getDefaultTexture().getID());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(texturePack.getrTexture().getType(), texturePack.getrTexture().getID());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
        GLES20.glBindTexture(texturePack.getgTexture().getType(), texturePack.getgTexture().getID());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
        GLES20.glBindTexture(texturePack.getbTexture().getType(), texturePack.getbTexture().getID());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE4);
        GLES20.glBindTexture(terrain.getBlendMap().getType(), terrain.getBlendMap().getID());
    }

    private void loadModelMatrix(Terrain terrain) {
        shader.loadTransformationMatrix(new Matrix4f(new Vector3f(terrain.getX(), 0.0f, terrain.getZ()), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f)));
    }

    public void render(List<Terrain> terrains) {
        for (Terrain terrain : terrains) {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, terrain.getModel().getVertexNum(), GLES20.GL_UNSIGNED_INT, 0);
            disableTerrain(terrain);
        }
    }

    private void disableTerrain(Terrain terrain) {
        terrain.getModel().disable();
    }
}
