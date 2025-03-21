package de.einsjustin.controller.api.handlers;

import de.einsjustin.controller.api.model.Controller;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ControllerHandler {
  void updateControllerList();
  List<Controller> getControllers();
  void setSelectedController(String name);
  Controller getSelectedController();
}
