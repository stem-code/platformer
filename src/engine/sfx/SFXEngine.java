package engine.sfx;

import engine.math.Vector3f;
import org.lwjgl.openal.*;

public class SFXEngine {
    private long device;
    private long context;

    public SFXEngine() {
        String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        device = ALC10.alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        context = ALC10.alcCreateContext(device, attributes);
        ALC10.alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            System.err.println("Unable to initialize OpenAL");
        }
    }

    public void setListener(Vector3f position, Vector3f velocity) {
        AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
        AL10.alListener3f(AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }

    public void close() {
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }
}
