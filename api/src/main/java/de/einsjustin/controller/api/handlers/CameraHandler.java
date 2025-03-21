package de.einsjustin.controller.api.handlers;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface CameraHandler {

  float getForwardMovingSpeed();

  float getStrafeMovingSpeed();

  void turn(double x, double y);
}
