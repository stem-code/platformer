package tests;

import engine.core.GameConfigs;
import engine.core.Window;
import engine.gfx.GFXEngine;
import engine.gfx.models.Model;
import engine.io.input.gfxrelated.OBJFile;
import engine.io.input.gfxrelated.PNGFile;
import engine.math.MathUtils;
import engine.math.Vector3f;
import engine.ui.Keyboard;
import tests.entities.Camera;
import tests.entities.Light;
import tests.entities.Player;
import tests.models.SkinnedModel;
import tests.particles.Particle;
import tests.particles.ParticleSystem;
import tests.statics.StaticEntity;
import tests.terrains.Terrain;
import tests.textures.Skin;
import tests.textures.TerrainTexture;
import tests.textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;

public class GraphicsTest {
    private static final int PINE_TREE_DENSITY = 400;
    private static final int FERN_DENSITY = 200;
    private static final int GRASS_DENSITY = 200;
    private static final int ENTITY_SPAWN_AREA = 800;

    public static void main(String[] args) {
        GFXEngine gfxEngine = new GFXEngine(new GameConfigs().setWindowTitle("Hello World!").setWindowWidth(1500).setWindowHeight(900).setWindowSamples(8));

        //GFXEngine.getClearColor().set(0.5f, 0.75f, 0.5f);
        //GFXEngine.getClearColor().set(0.3f, 0.5f, 0.75f);
        GFXEngine.getClearColor().set(0.55f, 0.6f, 0.65f);

        Model playerModel = new Model(new OBJFile("res/statics/lamp/lampModel"));
        Skin playerSkin = new Skin(new PNGFile("res/statics/lamp/lampTexture"));
        Player player = new Player(new SkinnedModel(playerModel, playerSkin), new Vector3f(400.0f, 0.0f, 400.0f), new Vector3f());

        Camera camera = new Camera(player, new Vector3f(0.0f, 5.0f, 0.0f));
        Light light = new Light(new Vector3f(20000.0f, 20000.0f, 2000.0f), new Vector3f(1.0f, 1.0f, 1.0f));

        TerrainTexture blendMap = new TerrainTexture(new PNGFile("res/terrains/blendMaps/blendMapCustom"));
        TerrainTexturePack texturePack = new TerrainTexturePack(
                new TerrainTexture(new PNGFile("res/terrains/textures/grass")),
                new TerrainTexture(new PNGFile("res/terrains/textures/mud")),
                new TerrainTexture(new PNGFile("res/terrains/textures/grassFlowers")),
                new TerrainTexture(new PNGFile("res/terrains/textures/path"))
        );
        Terrain terrain = new Terrain(0, 0, blendMap, texturePack);

        Model pineTreeModel = new Model(new OBJFile("res/statics/pineTree/pineTreeModel"));
        Skin pineTreeSkin = new Skin(new PNGFile("res/statics/pineTree/pineTreeTexture"));
        List<StaticEntity> pineTrees = new ArrayList<>();
        for (int i = 0;i < PINE_TREE_DENSITY;i++) {
            pineTrees.add(new StaticEntity(new SkinnedModel(pineTreeModel, pineTreeSkin),
                    new Vector3f(MathUtils.random() * ENTITY_SPAWN_AREA, 0.0f, MathUtils.random() * ENTITY_SPAWN_AREA),
                    new Vector3f(0.0f, MathUtils.random() * 360, 0.0f),
                    MathUtils.random() * 0.5f + 1.5f));
        }

        Model fernModel = new Model(new OBJFile("res/statics/fern/fernModel"));
        Skin fernSkin = new Skin(new PNGFile("res/statics/fern/fernTexture")).setHasTransparency(true);
        List<StaticEntity> ferns = new ArrayList<>();
        for (int i = 0;i < FERN_DENSITY;i++) {
            ferns.add(new StaticEntity(new SkinnedModel(fernModel, fernSkin),
                    new Vector3f(MathUtils.random() * ENTITY_SPAWN_AREA, 0.0f, MathUtils.random() * ENTITY_SPAWN_AREA),
                    new Vector3f(0.0f, MathUtils.random() * 360, 0.0f),
                    0.5f));
        }

        Model grassModel = new Model(new OBJFile("res/statics/grass/grassModel"));
        Skin grassSkin = new Skin(new PNGFile("res/statics/grass/grassTexture")).setHasTransparency(true).setUseFakeLighting(true);
        List<StaticEntity> grass = new ArrayList<>();
        for (int i = 0;i < GRASS_DENSITY;i++) {
            grass.add(new StaticEntity(new SkinnedModel(grassModel, grassSkin),
                    new Vector3f(MathUtils.random() * ENTITY_SPAWN_AREA, 0.0f, MathUtils.random() * ENTITY_SPAWN_AREA),
                    new Vector3f(0.0f, MathUtils.random() * 360, 0.0f)));
        }

        ParticleSystem particleSystem = new ParticleSystem(50.0f, 25.0f, -15.0f, 4.0f);

        long startTime = System.currentTimeMillis();
        while (!Window.isCloseRequested()) {
            float delta = (System.currentTimeMillis() - startTime) / 1000.0f;
            startTime = System.currentTimeMillis();
            System.out.println("Works! Delta: " + delta);

            player.move(delta);
            camera.move(delta);
            particleSystem.generateParticles(delta, player.getPosition());
            //camera.getPosition().set(player.getPosition().clone().add(0.0f, 5.0f, 15.0f));

            gfxEngine.processStaticEntity(player);
            for (StaticEntity entity : pineTrees) {
                gfxEngine.processStaticEntity(entity);
            }
            for (StaticEntity entity : ferns) {
                gfxEngine.processStaticEntity(entity);
            }
            for (StaticEntity entity : grass) {
                gfxEngine.processStaticEntity(entity);
            }
            gfxEngine.processTerrain(terrain);
            gfxEngine.render(camera, light, delta);
        }

        gfxEngine.close();
    }
}
