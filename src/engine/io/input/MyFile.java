package engine.io.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MyFile {
    private static final String FILE_BEGIN = "./";
    protected static final String FILE_SEPARATOR = "/";

    private String path;
    private String name;
    private String fileType;

    public MyFile(String path, String fileType) {
        this.path = FILE_BEGIN + path  + fileType;
        String[] s = path.split(FILE_SEPARATOR);
        this.name = s[s.length - 1];
        this.fileType = fileType;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(new File(path));
    }

    public String getAsText() {
        String text = "";
        try {
            Scanner scanner = new Scanner(getInputStream());
            while (scanner.hasNext()) {
                text += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("File IO Error: Error reading file!");
            e.printStackTrace();
        }
        return text;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getFileType() {
        return fileType;
    }
}
