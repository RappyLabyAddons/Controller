package de.einsjustin.controller.v1_20_1;

import de.einsjustin.controller.api.CameraHandler;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Unique;

@Implements(CameraHandler.class)
public class VersionedCameraHandler implements CameraHandler {

  public float getForwardMovingSpeed() {
    LocalPlayer player = this.getClientPlayer();
    return player != null ? player.input.forwardImpulse : 0;
  }

  public float getStrafeMovingSpeed() {
    LocalPlayer player = this.getClientPlayer();
    return player != null ? player.input.leftImpulse : 0;
  }

  @Override
  public void turn(double x, double y) {
    LocalPlayer player = this.getClientPlayer();
    if (player == null) {
      return;
    }
    player.turn(x, y);
  }

  @Unique
  private LocalPlayer getClientPlayer() {
    return Minecraft.getInstance().player;
  }
}
