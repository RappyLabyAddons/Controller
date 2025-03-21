package de.einsjustin.controller.listener;

import de.einsjustin.controller.ControllerAddon;
import de.einsjustin.controller.api.Controller;
import de.einsjustin.controller.api.ControllerHandler;
import de.einsjustin.controller.event.ControllerInputEvent;
import de.einsjustin.controller.event.ControllerStickEvent;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent.State;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;

public class GameTickListener {

  private final ControllerHandler controllerHandler;

  private final boolean[] pressedKeys = new boolean[GLFW.GLFW_KEY_LAST + 1];

  public GameTickListener() {
    this.controllerHandler = ControllerAddon.references().controllerHandler();
  }

  @Subscribe
  public void onGameTick(GameTickEvent event) {
    Controller selectedController = this.controllerHandler.getSelectedController();
    if (selectedController == null) return;
    int id = selectedController.id();

    if (GLFW.glfwJoystickPresent(id)) {
      GLFWGamepadState glfwGamepadState = GLFWGamepadState.create();

      if (GLFW.glfwGetGamepadState(id, glfwGamepadState)) {
        // Handle Buttons
        for (int buttonId = 0; buttonId <= GLFW.GLFW_GAMEPAD_BUTTON_LAST; buttonId++) {
          boolean pressed = glfwGamepadState.buttons(buttonId) == GLFW.GLFW_PRESS;
          if (pressed && !this.pressedKeys[buttonId]) {
            this.fireInputEvent(selectedController, buttonId, State.PRESS, false);
            this.pressedKeys[buttonId] = true;
          } else if (pressed && this.pressedKeys[buttonId]) {
            this.fireInputEvent(selectedController, buttonId, State.HOLDING, false);
          } else if (!pressed && this.pressedKeys[buttonId]) {
            this.fireInputEvent(selectedController, buttonId, State.UNPRESSED, false);
            this.pressedKeys[buttonId] = false;
          }
        }

        // Handle Triggers
        float ltValue = glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER);
        float rtValue = glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);
        float triggerThreshold = 0.0f;

        // LT (Left Trigger)
        if (ltValue > triggerThreshold && !this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER]) {
          this.fireInputEvent(
              selectedController,
              GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER,
              State.PRESS,
              true
          );
          this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER] = true;
        } else if (ltValue > triggerThreshold) {
          this.fireInputEvent(
              selectedController,
              GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER,
              State.HOLDING,
              true
          );
        } else if (this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER]) {
          this.fireInputEvent(
              selectedController,
              GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER,
              State.UNPRESSED,
              true
          );
          this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER] = false;
        }

        // RT (Right Trigger)
        if (rtValue > triggerThreshold && !this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER]) {
          this.fireInputEvent(selectedController,
              GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER,
              State.PRESS,
              true
          );
          this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER] = true;
        } else if (rtValue > triggerThreshold) {
          this.fireInputEvent(
              selectedController,
              GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER,
              State.HOLDING,
              true
          );
        } else if (this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER]) {
          this.fireInputEvent(
              selectedController,
              GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER,
              State.UNPRESSED,
              true
          );
          this.pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER] = false;
        }

        this.fireStickEvent(
            selectedController,
            glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_X),
            glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y),
            glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_X),
            glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_Y)
        );
      }
    }
  }

  private void fireInputEvent(Controller selectedController, int buttonId, State state,
      boolean trigger) {
    Laby.fireEvent(new ControllerInputEvent(selectedController, buttonId, state, trigger));
  }

  private void fireStickEvent(Controller selectedController, float moveX, float moveY, float lookX,
      float lookY) {
    Laby.fireEvent(new ControllerStickEvent(selectedController, moveX, moveY, lookX, lookY));
  }
}
