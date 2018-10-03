package tests.textures;

import engine.gfx.glwrapper.textures.Texture;
import engine.io.input.gfxrelated.PNGFile;
import org.lwjgl.opengles.*;

public class Skin extends Texture {
    private float reflectivity = 0.0f;
    private float shineDamper = 1.0f;

    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;

    public Skin(PNGFile pngFile) {
        super(GLES20.GL_TEXTURE_2D);
        this.bind();
        GLES20.glTexImage2D(getType(), 0, GLES20.GL_RGBA, pngFile.getWidth(), pngFile.getHeight(), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pngFile.getData());
        GLES20.glGenerateMipmap(getType());
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        if (GLES.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            float amount = Math.min(20.0f, GLES20.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
            GLES20.glTexParameterf(getType(), GLES30.GL_MAX_TEXTURE_LOD_BIAS, 0.0f);
            GLES20.glTexParameterf(getType(), EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
        }
        /*GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);*/
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(getType(), GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        this.unbind();
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public Skin setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
        return this;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public Skin setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
        return this;
    }

    public boolean hasTransparency() {
        return hasTransparency;
    }

    public Skin setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
        return this;
    }

    public boolean useFakeLighting() {
        return useFakeLighting;
    }

    public Skin setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
        return this;
    }
}
