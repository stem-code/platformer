package engine.io.input.gfxrelated;

import engine.io.input.XMLFile;

public class DAEFile extends XMLFile {
    private static final String FILE_TYPE_LOCK = ".dae";

    public DAEFile(String path) {
        super(path, FILE_TYPE_LOCK);
    }
}
