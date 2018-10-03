package engine.sfx.alWrapper;

import engine.io.input.sfxrelated.OGGFile;
import org.lwjgl.openal.AL10;

public class AudioBuffer {
    private int ID;
    private int format;

    public AudioBuffer(OGGFile oggFile) {
        format = -1;
        if (oggFile.getChannels() == 1) {
            format = AL10.AL_FORMAT_MONO16;
        } else if (oggFile.getChannels() > 1) {
            format = AL10.AL_FORMAT_STEREO16;
        }
        ID = AL10.alGenBuffers();
        AL10.alBufferData(ID, format, oggFile.getData(), oggFile.getSampleRate());
    }

    public void delete() {
        AL10.alDeleteBuffers(ID);
    }

    public int getID() {
        return ID;
    }
}
