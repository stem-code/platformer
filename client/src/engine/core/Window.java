package engine.core;

import engine.ui.Keyboard;
import engine.ui.Mouse;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengles.GLES;
import org.lwjgl.opengles.GLES20;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.ArrayList;

public final class Window {
    private static ArrayList<WindowResizeListener> resizeListeners = new ArrayList<>();
    private static long window;
    private static int width;
    private static int height;
    private static boolean exists;

    public static void create(String title, int width, int height, int samples) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, samples);

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_OPENGL_ES_API);

        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Unable to create GLFW window");
        }

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        });

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(window, pWidth, pHeight);
            GLFW.glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
                @Override
                public void invoke(long window, int width, int height) {
                    Window.width = width;
                    Window.height = height;
                    if (exists) {
                        for (WindowResizeListener resizeListener : resizeListeners) {
                            resizeListener.onResize(width, height);
                        }
                        GLES20.glViewport(0, 0, width, height);
                    }
                }
            });
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
        }
        GLFW.glfwMakeContextCurrent(window);

        Configuration.OPENGLES_EXPLICIT_INIT.set(true);
        GLES.create(GL.getFunctionProvider());
        GLES.createCapabilities();

        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);

        /*PNGFile rawIcon = new PNGFile("res/testIcon");
        GLFWImage icon = GLFWImage.malloc();
        GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
        icon.set(rawIcon.getWidth(), rawIcon.getHeight(), rawIcon.getByteBuffer());
        iconBuffer.put(icon);
        GLFW.glfwSetWindowIcon(window, iconBuffer);*/

        exists = true;

        Keyboard.create(window);
        Mouse.create(window);
    }

    public static void update() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public static boolean isCloseRequested() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public static void close() {
        exists = false;
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        GLES.destroy();
    }

    public static void addResizeListener(WindowResizeListener resizeListener) {
        resizeListeners.add(resizeListener);
    }

    public static void removeResizeListener(WindowResizeListener resizeListener) {
        resizeListeners.remove(resizeListener);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static boolean exists() {
        return exists;
    }

    public interface WindowResizeListener {
        void onResize(int width, int height);
    }
}