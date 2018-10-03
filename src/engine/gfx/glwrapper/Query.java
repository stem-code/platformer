package engine.gfx.glwrapper;

import org.lwjgl.opengles.GLES30;

public class Query extends GLObject {
    private int ID;
    private final int type;

    public Query(int type) {
        ID = GLES30.glGenQueries();
        this.type = type;
    }

    public void start() {
        GLES30.glBeginQuery(type, ID);
    }

    public void stop() {
        GLES30.glEndQuery(type);
    }

    public int isResultReady() {
        return GLES30.glGetQueryObjectui(ID, GLES30.GL_QUERY_RESULT_AVAILABLE);
    }

    public int getResult() {
        return GLES30.glGetQueryObjectui(ID, GLES30.GL_QUERY_RESULT);
    }

    @Override
    public void deleteFromGL() {
        GLES30.glDeleteQueries(ID);
    }
}
