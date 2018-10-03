package engine.io.input.gfxrelated;

import engine.io.input.MyFile;
import org.newdawn.slick.opengl.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class PNGFile extends MyFile {
    private static final String FILE_TYPE_LOCK = ".png";

    private int width;
    private int height;
    private ByteBuffer data;

    public PNGFile(String path) {
        super(path, FILE_TYPE_LOCK);
        try {
            InputStream inputStream = getInputStream();
            PNGDecoder pngDecoder = new PNGDecoder(inputStream);
            width = pngDecoder.getWidth();
            height = pngDecoder.getHeight();
            data = ByteBuffer.allocateDirect(4 * width * height);
            pngDecoder.decode(data, width * 4, PNGDecoder.RGBA);
            data.flip();
            inputStream.close();
        } catch (IOException e) {
            System.err.println("Could not read PNG file.");
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getData() {
        return data;
    }
}
