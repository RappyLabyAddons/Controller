package de.einsjustin.controller.api.handlers;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface CameraHandler { // TODO: Implement versions
  void turn(double x, double y);
}
