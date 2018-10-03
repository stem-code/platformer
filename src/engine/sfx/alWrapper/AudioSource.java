package engine.sfx.alWrapper;

import org.lwjgl.openal.AL10;

public class AudioSource {
    private int ID;

    public AudioSource() {
        ID = AL10.alGenSources();
        AL10.alSourcef(ID, AL10.AL_GAIN, 1.0f);
        AL10.alSourcef(ID, AL10.AL_PITCH, 0.5f);
        AL10.alSource3f(ID, AL10.AL_POSITION, 0.0f, 0.0f, 0.0f);
    }

    public void assignBuffer(AudioBuffer buffer) {
        AL10.alSourcei(ID, AL10.AL_BUFFER, buffer.getID());
    }

    public void play(AudioBuffer buffer) {
        assignBuffer(buffer);
        AL10.alSourcePlay(ID);
    }

    public void delete() {
        AL10.alDeleteBuffers(ID);
    }

    public int getID() {
        return ID;
    }
}
