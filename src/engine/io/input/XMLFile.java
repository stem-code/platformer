package engine.io.input;

public class XMLFile extends MyFile {
    private static final String FILE_TYPE_LOCK = ".xml";

    public XMLFile(String path) {
        super(path, FILE_TYPE_LOCK);
    }

    protected XMLFile(String path, String fileType) {
        super(path, fileType);
    }
}
