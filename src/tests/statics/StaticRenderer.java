package tests.statics;

import engine.gfx.GFXEngine;
import engine.math.Matrix4f;
import org.lwjgl.opengles.GLES20;
import tests.models.SkinnedModel;
import tests.textures.Skin;

import java.util.List;
import java.util.Map;

public class StaticRenderer {
    StaticShader shader;

    public StaticRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    private void prepareSkinnedModel(SkinnedModel model) {
        model.getModel().enable();
        Skin skin = model.getSkin();
        if (skin.hasTransparency()) {
            GFXEngine.disableCulling();
        }
        shader.loadUseFakeLighting(skin.useFakeLighting());
        shader.loadShineVariables(skin.getReflectivity(), skin.getShineDamper());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(skin.getType(), skin.getID());
    }

    private void prepareInstance(StaticEntity entity) {
        shader.loadTransformationMatrix(new Matrix4f(entity.getPosition(), entity.getRotation(), entity.getScale()));
    }

    public void render(Map<SkinnedModel, List<StaticEntity>> statics) {
        for (SkinnedModel model : statics.keySet()) {
            prepareSkinnedModel(model);
            for (StaticEntity entity : statics.get(model)) {
                prepareInstance(entity);
                GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getModel().getVertexNum(), GLES20.GL_UNSIGNED_INT, 0);
            }
            disableSkinnedModel(model);
        }
    }

    private void disableSkinnedModel(SkinnedModel model) {
        GFXEngine.enableCulling();
        model.getModel().disable();
    }
}
