package tests.entities;

import engine.math.MathUtils;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.ui.Keyboard;
import engine.ui.Mouse;

public class Camera {
    private static final float MOVE_SPEED = 15.0f;

    private Player player;
    private Vector3f offset;
    private Vector3f position;
    private Vector3f rotation;

    private float distance = 30.0f;
    private float rotationAroundPlayer = 0.0f;

    private Vector2f lastMousePos = new Vector2f();

    public Camera(Player player, Vector3f offset) {
        this.player = player;
        this.offset = offset;
        position = new Vector3f();
        rotation = new Vector3f(MathUtils.toRadians(20.0f), 0.0f, 0.0f);
    }

    public void move(float delta) {
        alterZoom(delta);
        if (Mouse.isButtonDown(Mouse.BUTTON_LEFT)) {
            rotationAroundPlayer += (Mouse.getCursorX() - lastMousePos.x) / 500.0f;
            rotation.x += (Mouse.getCursorY() - lastMousePos.y) / 500.0f;
        }
        lastMousePos.set(Mouse.getCursorX(), Mouse.getCursorY());
        rotation.y = -player.getRotation().y + MathUtils.PI + rotationAroundPlayer;
        float horizontalDst = distance * MathUtils.cos(rotation.x);
        float verticalDst = player.getPosition().y + MathUtils.sin(rotation.x) * distance;
        float xDst = player.getPosition().x - MathUtils.sin(rotation.y) * horizontalDst;
        float zDst = player.getPosition().z + MathUtils.cos(rotation.y) * horizontalDst;
        position.set(xDst, verticalDst, zDst);
        position.add(offset);
    }

    private void alterZoom(float delta) {
        if (Keyboard.isKeyPressed(Keyboard.KEY_EQUAL)) {
            distance -= 10.0f *  delta;
        } else if (Keyboard.isKeyPressed(Keyboard.KEY_MINUS)) {
            distance += 10.0f * delta;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotate(rotation);
        viewMatrix.translate(position.clone().neg());
        return viewMatrix;
    }
}
