package tests;

import engine.core.Game;
import engine.core.GameConfigs;
import engine.core.State;

public class Main extends Game {
    @Override
    protected State initState() {
        return new TestState();
    }

    public Main(GameConfigs configs) {
        super(configs);
    }

    public static void main(String[] args) {
        new Main(new GameConfigs().setWindowTitle("Hello World!").setWindowWidth(1500).setWindowHeight(900).setWindowSamples(8));
    }
}
