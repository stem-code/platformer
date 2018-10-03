package engine.ui;

import engine.core.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.util.ArrayList;
import java.util.Arrays;

public final class Mouse {
    public static final int MOUSE_SIZE = 16;
    public static final int
            BUTTON_LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT,
            BUTTON_RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT,
            BUTTON_MIDDLE = GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
    public static final int
            ACTION_RELEASE = GLFW.GLFW_RELEASE,
            ACTION_PRESS = GLFW.GLFW_PRESS;

    private static ArrayList<MouseListener> mouseListeners = new ArrayList<>();
    private static float cursorX;
    private static float cursorY;
    private static int[] buttonActions = new int[MOUSE_SIZE];
    private static float scrollX;
    private static float scrollY;
    private static float previousScrollX = 0.0f;
    private static float previousScrollY = 0.0f;

    private Mouse() {
    }

    public static void create(long window) {
        if (Window.exists()) {
            GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
                @Override
                public void invoke(long window, double xpos, double ypos) {
                    cursorX = (float) xpos;
                    cursorY = (float) ypos;
                    for (MouseListener mouseListener : mouseListeners) {
                        mouseListener.onCursorMoved(cursorX, cursorY);
                    }
                }
            });
            GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
                @Override
                public void invoke(long window, int button, int action, int mods) {
                    buttonActions[button] = action;
                    for (MouseListener mouseListener : mouseListeners) {
                        mouseListener.onMouseButtonAction(button, action);
                    }
                }
            });
            GLFW.glfwSetScrollCallback(window, new GLFWScrollCallback() {
                @Override
                public void invoke(long window, double xoffset, double yoffset) {
                    scrollX = (float) xoffset - previousScrollX;
                    scrollY = (float) yoffset - previousScrollY;

                    for (MouseListener mouseListener : mouseListeners) {
                        mouseListener.onScroll(scrollX, scrollY);
                    }
                }
            });
        }

    }

    public static void addMouseListener(MouseListener mouseListener) {
        mouseListeners.add(mouseListener);
    }

    public static void removeMouseListener(MouseListener mouseListener) {
        mouseListeners.remove(mouseListener);
    }

    public static float getCursorX() {
        return cursorX;
    }

    public static float getCursorY() {
        return cursorY;
    }

    public static boolean isButtonDown(int button) {
        return buttonActions[button] != GLFW.GLFW_RELEASE;
    }

    public static int getButtonAction(int button) {
        return buttonActions[button];
    }

    public static float getScrollX() {
        return scrollX;
    }

    public static float getScrollY() {
        return scrollY;
    }

    public static void print() {
        System.out.println("Cursor Position: (" + cursorX + ", " + cursorY + ")");
        System.out.println("Mouse Button States: " + Arrays.toString(buttonActions));
        System.out.println("Scroll Values: (" + scrollX + ", " + scrollY + ")");
    }

    public interface MouseListener {
        void onCursorMoved(float cursorX, float cursorY);
        void onMouseButtonAction(int button, int action);
        void onScroll(float scrollX, float scrollY);
    }
}
