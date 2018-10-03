package engine.core;

import engine.gfx.GFXEngine;
import engine.sfx.SFXEngine;
import engine.ui.Keyboard;
import engine.ui.Mouse;
import org.lwjgl.opengles.GLES20;

public abstract class Game implements Window.WindowResizeListener {
    private GFXEngine gfxEngine;
    private SFXEngine sfxEngine;
    private State state;

    protected abstract State initState();

    public Game(GameConfigs configs) {
        gfxEngine = new GFXEngine(configs);
        sfxEngine = new SFXEngine();

        State.game = this;
        state = initState();

        long startTime = System.currentTimeMillis();
        while (!Window.isCloseRequested()) {
            state.mainThread((System.currentTimeMillis() - startTime) / 1000.0f);
            Window.update();
        }
        state.onDestroy();
        gfxEngine.close();
        sfxEngine.close();
    }

    @Override
    public final void onResize(int width, int height) {
        if (state != null) {
            state.onResize(width, height);
            GLES20.glViewport(0, 0, width, height);
        }
    }

    public final void setState(State state) {
        this.state.onDestroy();
        this.state = state;
    }
}
