package engine.core;

/**
 * @author Deven
 * Created on 2/8/2018.
 * Last edited on 6/7/2018.
 */

public abstract class State {
    static Game game;

    protected abstract void mainThread(float delta);

    protected void onResize(int width, int height) {
    }

    protected void onKeyPressed(int key, int scanCode, int action, int mods) {
    }

    protected void onCursorMoved(float cursorX, float cursorY) {
    }

    protected void onMouseButtonAction(int button, int action) {
    }

    protected void onScroll(float scrollX, float scrollY) {
    }

    protected void onDestroy() {
    }

    protected final void setState(State state) {
        game.setState(state);
    }
}
