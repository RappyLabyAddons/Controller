package de.einsjustin.controller.api;

import net.labymod.api.reference.annotation.Referenceable;
import java.util.List;

@Referenceable
public interface ControllerHandler {
  void updateControllerList();
  List<Controller> getControllers();
  void setSelectedController(String name);
  Controller getSelectedController();
}
