package de.einsjustin.controller;

import de.einsjustin.controller.api.Controller;
import net.labymod.api.models.Implements;
import org.lwjgl.glfw.GLFW;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Implements(de.einsjustin.controller.api.ControllerHandler.class)
public class DefaultControllerHandler implements de.einsjustin.controller.api.ControllerHandler {

  private final List<Controller> controllers = new ArrayList<>();
  private Controller selectedController;

  public DefaultControllerHandler() {
    if (!GLFW.glfwInit()) {
      throw new RuntimeException("Failed to initialize GLFW");
    }
    updateControllerList();
  }

  public void updateControllerList() {
    controllers.clear();
    for (int id = GLFW.GLFW_JOYSTICK_1; id < GLFW.GLFW_JOYSTICK_16; id++) {
      if (GLFW.glfwJoystickPresent(id)) {
        String name = GLFW.glfwGetJoystickName(id);
        String guid = GLFW.glfwGetJoystickGUID(id);
        controllers.add(new Controller(name, guid, id));
      }
    }
  }

  public List<Controller> getControllers() {
    return controllers;
  }

  public void setSelectedController(String name) {
    for (Controller controller : controllers) {
      if (controller.name().equals(name)) {
        this.selectedController = controller;
        break;
      }
    }
  }

  public Controller getSelectedController() {
    return selectedController;
  }
}
