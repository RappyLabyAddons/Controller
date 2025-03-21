package de.einsjustin.controller;

import de.einsjustin.controller.api.handlers.ControllerHandler;
import de.einsjustin.controller.api.model.Controller;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import org.lwjgl.glfw.GLFW;

@Singleton
@Implements(ControllerHandler.class)
public class DefaultControllerHandler implements ControllerHandler {

  private final List<Controller> controllers = new ArrayList<>();
  private Controller selectedController;

  public DefaultControllerHandler() {
    if (!GLFW.glfwInit()) {
      throw new RuntimeException("Failed to initialize GLFW");
    }
    this.updateControllerList();
  }

  public void updateControllerList() {
    this.controllers.clear();
    for (int id = GLFW.GLFW_JOYSTICK_1; id < GLFW.GLFW_JOYSTICK_16; id++) {
      if (GLFW.glfwJoystickPresent(id)) {
        String name = GLFW.glfwGetJoystickName(id);
        String guid = GLFW.glfwGetJoystickGUID(id);
        this.controllers.add(new Controller(name, guid, id));
      }
    }
  }

  public List<Controller> getControllers() {
    return this.controllers;
  }

  public void setSelectedController(String name) {
    for (Controller controller : this.controllers) {
      if (controller.name().equals(name)) {
        this.selectedController = controller;
        break;
      }
    }
  }

  public Controller getSelectedController() {
    return this.selectedController;
  }
}
