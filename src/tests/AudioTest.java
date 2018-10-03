package tests;

import engine.io.input.sfxrelated.OGGFile;
import engine.math.Vector3f;
import engine.sfx.SFXEngine;
import engine.sfx.alWrapper.AudioBuffer;
import engine.sfx.alWrapper.AudioSource;

import java.util.Scanner;

public class AudioTest {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SFXEngine sfxEngine = new SFXEngine();
        sfxEngine.setListener(new Vector3f(), new Vector3f());

        AudioBuffer buffer = new AudioBuffer(new OGGFile("res/audio/bounce"));
        AudioSource source = new AudioSource();

        Scanner s = new Scanner(System.in);
        String line = "";
        while (!line.equals("q")) {
            if (line.equals("p")) {
                source.play(buffer);
            }
            line = s.nextLine();
        }

        buffer.delete();
        source.delete();
        sfxEngine.close();
    }
}
