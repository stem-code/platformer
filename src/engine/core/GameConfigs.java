package engine.core;

import engine.math.Vector3f;

public class GameConfigs {
    private String windowTitle = "";
    private int windowWidth = 1500;
    private int windowHeight = 900;
    private int windowSamples = 1;
    private Vector3f clearColor = new Vector3f();

    public GameConfigs() {
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public GameConfigs setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public GameConfigs setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
        return this;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public GameConfigs setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
        return this;
    }

    public int getWindowSamples() {
        return windowSamples;
    }

    public GameConfigs setWindowSamples(int windowSamples) {
        this.windowSamples = windowSamples;
        return this;
    }


}
