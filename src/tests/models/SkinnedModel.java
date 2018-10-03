package tests.models;

import engine.gfx.models.Model;
import tests.textures.Skin;

public class SkinnedModel {
    private Model model;
    private Skin skin;

    public SkinnedModel(Model model, Skin skin) {
        this.model = model;
        this.skin = skin;
    }

    public Model getModel() {
        return model;
    }

    public Skin getSkin() {
        return skin;
    }
}
