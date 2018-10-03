package engine.gfx.glwrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class GLObject {
    private static List<GLObject> glObjects = new ArrayList<>();

    public GLObject() {
        glObjects.add(this);
    }

    protected abstract void deleteFromGL();

    public final void delete() {
        deleteFromGL();
        glObjects.remove(this);
    }

    public static void cleanUp() {
        for (int i = glObjects.size() - 1;i >= 0;i--) {
            glObjects.get(i).delete();
        }
    }
}
