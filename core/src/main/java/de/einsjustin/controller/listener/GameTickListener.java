package de.einsjustin.controller.listener;

import de.einsjustin.controller.ControllerAddon;
import de.einsjustin.controller.api.Controller;
import de.einsjustin.controller.api.ControllerHandler;
import de.einsjustin.controller.event.ControllerInputEvent;
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

    Controller selectedController = controllerHandler.getSelectedController();
    if (selectedController == null) return;
    int id = selectedController.id();

    if (GLFW.glfwJoystickPresent(id)) {

      GLFWGamepadState glfwGamepadState = GLFWGamepadState.create();

      if (GLFW.glfwGetGamepadState(id, glfwGamepadState)) {
        // Handle Buttons
        for (int buttonId = 0; buttonId <= GLFW.GLFW_GAMEPAD_BUTTON_LAST; buttonId++) {
          boolean pressed = glfwGamepadState.buttons(buttonId) == GLFW.GLFW_PRESS;
          if (pressed && !pressedKeys[buttonId]) {
            fireEvent(selectedController, buttonId, State.PRESS);
            pressedKeys[buttonId] = true;
          } else if (pressed && pressedKeys[buttonId]) {
            fireEvent(selectedController, buttonId, State.HOLDING);
          } else if (!pressed && pressedKeys[buttonId]) {
            fireEvent(selectedController, buttonId, State.UNPRESSED);
            pressedKeys[buttonId] = false;
          }
        }

        // Handle Triggers
        float ltValue = glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER);
        float rtValue = glfwGamepadState.axes(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);
        float triggerThreshold = 0.5f; // Adjust this threshold as needed

        // LT (Left Trigger)
        if (ltValue > triggerThreshold && !pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER]) {
          fireEvent(selectedController, GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER, State.PRESS);
          pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER] = true;
        } else if (ltValue > triggerThreshold) {
          fireEvent(selectedController, GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER, State.HOLDING);
        } else if (pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER]) {
          fireEvent(selectedController, GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER, State.UNPRESSED);
          pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER] = false;
        }

        // RT (Right Trigger)
        if (rtValue > triggerThreshold && !pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER]) {
          fireEvent(selectedController, GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER, State.PRESS);
          pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER] = true;
        } else if (rtValue > triggerThreshold) {
          fireEvent(selectedController, GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER, State.HOLDING);
        } else if (pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER]) {
          fireEvent(selectedController, GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER, State.UNPRESSED);
          pressedKeys[GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER] = false;
        }
      }
    }
  }


  private void fireEvent(Controller selectedController, int buttonId, State state) {
    Laby.fireEvent(new ControllerInputEvent(selectedController, buttonId, state));
  }
}
