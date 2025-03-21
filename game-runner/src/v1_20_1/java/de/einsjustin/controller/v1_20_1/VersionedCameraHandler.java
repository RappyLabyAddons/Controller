package de.einsjustin.controller.v1_20_1;

import de.einsjustin.controller.api.handlers.CameraHandler;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

@Implements(CameraHandler.class)
public class VersionedCameraHandler implements CameraHandler {

  @Override
  public void turn(double x, double y) {
    LocalPlayer player = Minecraft.getInstance().player;
    if (player == null) {
      return;
    }
    player.turn(x, y);
  }
}
