package de.einsjustin.controller.listener;

import de.einsjustin.controller.ControllerAddon;
import de.einsjustin.controller.api.handlers.CameraHandler;
import de.einsjustin.controller.event.ControllerStickEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.event.Subscribe;

public class ControllerStickListener {

  private final static float SPEED = 8.0f;
  private final CameraHandler cameraHandler = ControllerAddon.references().cameraHandler();

  @Subscribe
  public void onStickMove(ControllerStickEvent event) {
    ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
    if (player == null) {
      return;
    }

    float moveX = this.applyDeadzone(event.moveX());
    float moveY = this.applyDeadzone(event.moveY());
    float lookX = this.applyDeadzone(event.lookX());
    float lookY = this.applyDeadzone(event.lookY());

    double sensitivity = this.getSensitivity() * 0.6 + 0.2;
    double adjustment = Math.pow(sensitivity, 3) * SPEED;

    if (lookX != 0.0f || lookY != 0.0f) {
      this.cameraHandler.turn(lookX * adjustment, lookY * adjustment);
    }

    MinecraftInputMapping forward = this.getMapping("key.forward");
    MinecraftInputMapping left = this.getMapping("key.left");
    MinecraftInputMapping back = this.getMapping("key.back");
    MinecraftInputMapping right = this.getMapping("key.right");

    if (Math.abs(moveX) > this.getDeadzone()) {
      if (moveX >= 0f) {
        if (left.isDown()) {
          left.unpress();
        }
        if (!right.isDown()) {
          right.press();
        }
      } else {
        if (right.isDown()) {
          right.unpress();
        }
        if (!left.isDown()) {
          left.press();
        }
      }
    } else {
      if (right.isDown()) {
        right.unpress();
      }
      if (left.isDown()) {
        left.unpress();
      }
    }

    if (Math.abs(moveY) > this.getDeadzone()) {
      if (moveY >= 0f) {
        if (forward.isDown()) {
          forward.unpress();
        }
        if (!back.isDown()) {
          back.press();
        }
      } else {
        if (back.isDown()) {
          back.unpress();
        }
        if (!forward.isDown()) {
          forward.press();
        }
      }
    } else {
      if (forward.isDown()) {
        forward.unpress();
      }
      if (back.isDown()) {
        back.unpress();
      }
    }
  }

  private MinecraftInputMapping getMapping(String key) {
    return Laby.labyAPI().minecraft().options().getInputMapping(key);
  }

  private float applyDeadzone(float value) {
    return Math.abs(value) < this.getDeadzone() ? 0 : value;
  }

  private float getDeadzone() {
    return 0.5f;
  }

  private float getSensitivity() {
    return 2f;
  }
}
