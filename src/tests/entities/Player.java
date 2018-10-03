package tests.entities;

import engine.math.MathUtils;
import engine.math.Vector3f;
import engine.ui.Keyboard;
import tests.models.SkinnedModel;
import tests.statics.StaticEntity;

public class Player extends StaticEntity {
    private static final float MOVE_SPEED = 25.0f;
    private static final float TURN_SPEED = 160.0f;
    private static final float GRAVITY = -50.0f;
    private static final float JUMP_POWER = 30.0f;

    private static final float TERRAIN_HEIGHT = 0.0f;

    private float moveVelocity = 0.0f;
    private float turnVelocity = 0.0f;
    private float verticalVelocity = 0.0f;

    private boolean isAirborne = false;

    public Player(SkinnedModel model) {
        super(model);
    }

    public Player(SkinnedModel model, Vector3f position, Vector3f rotation) {
        super(model, position, rotation);
    }

    public Player(SkinnedModel model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public Player(SkinnedModel model, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(model, position, rotation, scale);
    }

    public void move(float delta) {
        moveVelocity = 0.0f;
        turnVelocity = 0.0f;
        if (Keyboard.isKeyPressed(Keyboard.KEY_W)) {
            moveVelocity = MOVE_SPEED;
        } else if (Keyboard.isKeyPressed(Keyboard.KEY_S)) {
            moveVelocity = -MOVE_SPEED;
        }
        if (Keyboard.isKeyPressed(Keyboard.KEY_A)) {
            turnVelocity = TURN_SPEED;
        } else if (Keyboard.isKeyPressed(Keyboard.KEY_D)) {
            turnVelocity = -TURN_SPEED;
        }
        if (Keyboard.isKeyPressed(Keyboard.KEY_SPACE) && !isAirborne) {
            verticalVelocity = JUMP_POWER;
            isAirborne = true;
        }
        getRotation().add(0.0f, MathUtils.toRadians(turnVelocity) * delta, 0.0f);
        float distance = moveVelocity * delta;
        verticalVelocity += GRAVITY * delta;
        getPosition().add(MathUtils.sin(getRotation().y) * distance, verticalVelocity * delta, MathUtils.cos(getRotation().y) * distance);
        if (getPosition().y < TERRAIN_HEIGHT) {
            getPosition().y = TERRAIN_HEIGHT;
            isAirborne = false;
        }
    }
}
