package engine.gfx;

import engine.core.GameConfigs;
import engine.core.Window;
import engine.gfx.glwrapper.GLObject;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import org.lwjgl.opengles.GLES20;
import tests.entities.Camera;
import tests.entities.Light;
import tests.guis.GUI;
import tests.guis.GUIRenderer;
import tests.models.SkinnedModel;
import tests.particles.ParticleMaster;
import tests.particles.ParticleRenderer;
import tests.particles.ParticleShader;
import tests.skyboxes.Skybox;
import tests.skyboxes.SkyboxRenderer;
import tests.skyboxes.SkyboxShader;
import tests.statics.StaticEntity;
import tests.statics.StaticRenderer;
import tests.statics.StaticShader;
import tests.terrains.Terrain;
import tests.terrains.TerrainRenderer;
import tests.terrains.TerrainShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GFXEngine implements Window.WindowResizeListener {
    private static final float FOV = 70.0f;
    private static final float NEAR_PLANE = 0.001f;
    private static final float FAR_PLANE = 1000.0f;

    private static final float SKYBOX_TRANSITION_TIME = 3.0f;
    private static final float SKYBOX_WAIT_TIME = 15.0f;

    private static final Vector3f clearColor = new Vector3f(1.0f, 1.0f, 1.0f);

    private StaticShader staticShader;
    private StaticRenderer staticRenderer;
    private Map<SkinnedModel, List<StaticEntity>> statics = new HashMap<>();

    private TerrainShader terrainShader;
    private TerrainRenderer terrainRenderer;
    private List<Terrain> terrains = new ArrayList<>();

    private SkyboxShader skyboxShader;
    private SkyboxRenderer skyboxRenderer;
    private Skybox daySkybox;
    private Skybox nightSkybox;
    private boolean day = true;
    private boolean waiting = true;
    private float time = 0.0f;

    private GUIRenderer guiRenderer;
    private List<GUI> guis = new ArrayList<>();

    public GFXEngine(GameConfigs configs) {
        Window.create(configs.getWindowTitle(), configs.getWindowWidth(), configs.getWindowHeight(), configs.getWindowSamples());
        prepare();
        Window.update();
        //Configuration.OPENGLES_EXPLICIT_INIT.set(true);
        //GLES.create(GL.getFunctionProvider());
        //GLES.createCapabilities();

        enableCulling();
        Matrix4f projectionMatrix = Matrix4f.get3DProjectionMatrix(Window.getWidth(), Window.getHeight(), FOV, NEAR_PLANE, FAR_PLANE);

        staticShader = new StaticShader();
        staticRenderer = new StaticRenderer(staticShader, projectionMatrix);

        terrainShader = new TerrainShader();
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);

        ParticleMaster.init(projectionMatrix);

        daySkybox = new Skybox("res/skyboxes/daySkybox");
        nightSkybox = new Skybox("res/skyboxes/nightSkybox");
        skyboxShader = new SkyboxShader();
        skyboxRenderer = new SkyboxRenderer(skyboxShader, projectionMatrix, daySkybox);

        guiRenderer = new GUIRenderer();

        Window.addResizeListener(this);
    }

    public static void enableCulling() {
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
    }

    public static void disableCulling() {
        GLES20.glDisable(GLES20.GL_CULL_FACE);
    }

    private void prepare() {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(clearColor.x, clearColor.y, clearColor.z, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Camera camera, Light light, float delta) {
        ParticleMaster.update(delta);
        prepare();

        staticShader.start();
        staticShader.loadViewMatrix(camera);
        staticShader.loadFogColor(clearColor);
        staticShader.loadLight(light);
        staticRenderer.render(statics);
        staticShader.stop();
        statics.clear();

        terrainShader.start();
        terrainShader.loadViewMatrix(camera);
        terrainShader.loadFogColor(clearColor);
        terrainShader.loadLight(light);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();

        skyboxShader.start();
        skyboxShader.loadViewMatrix(camera);
        skyboxShader.loadFogColor(clearColor);
        skyboxShader.rotateSkybox(delta);
        if (skyboxRenderer.doneFading()) {
            if (!waiting) {
                if (day) {
                    skyboxRenderer.startFadeToSkybox(nightSkybox, SKYBOX_TRANSITION_TIME);
                    day = false;
                } else {
                    skyboxRenderer.startFadeToSkybox(daySkybox, SKYBOX_TRANSITION_TIME);
                    day = true;
                }
                waiting = true;
            } else {
                time += delta;
                if (time > SKYBOX_WAIT_TIME) {
                    time = 0.0f;
                    waiting = false;
                }
            }
        }
        skyboxRenderer.updateFadeToSkybox(delta);
        skyboxRenderer.render();
        skyboxShader.stop();

        ParticleMaster.renderParticles(camera);

        guiRenderer.render(guis);

        Window.update();
    }

    public void processStaticEntity(StaticEntity entity) {
        List<StaticEntity> batch = statics.get(entity.getModel());
        if (batch == null) {
            batch = new ArrayList<>();
            statics.put(entity.getModel(), batch);
        }
        batch.add(entity);
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processGUI(GUI gui) {
        guis.add(gui);
    }

    @Override
    public void onResize(int width, int height) {
        staticShader.start();
        staticShader.loadProjectionMatrix(Matrix4f.get3DProjectionMatrix(width, height, FOV, NEAR_PLANE, FAR_PLANE));
        staticShader.stop();
    }

    public void close() {
        GLObject.cleanUp();
        Window.close();
        //GLES.destroy();
    }

    public static Vector3f getClearColor() {
        return clearColor;
    }
}
