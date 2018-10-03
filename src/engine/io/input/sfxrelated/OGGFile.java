package engine.io.input.sfxrelated;

import engine.io.input.MyFile;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class OGGFile extends MyFile {
    private static final String FILE_TYPE_LOCK = ".ogg";

    private int channels;
    private int sampleRate;
    private ShortBuffer data;

    public OGGFile(String path) {
        super(path, FILE_TYPE_LOCK);
        MemoryStack.stackPush();
        IntBuffer channelsBuffer = MemoryStack.stackMallocInt(1);
        MemoryStack.stackPush();
        IntBuffer sampleRateBuffer = MemoryStack.stackMallocInt(1);

        data = STBVorbis.stb_vorbis_decode_filename(getPath(), channelsBuffer, sampleRateBuffer);

        channels = channelsBuffer.get();
        sampleRate = sampleRateBuffer.get();

        MemoryStack.stackPop();
        MemoryStack.stackPop();
    }

    public int getChannels() {
        return channels;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public ShortBuffer getData() {
        return data;
    }
}
