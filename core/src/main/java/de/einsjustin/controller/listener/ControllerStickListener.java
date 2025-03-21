package de.einsjustin.controller.listener;

import de.einsjustin.controller.ControllerAddon;
import de.einsjustin.controller.api.handlers.CameraHandler;
import de.einsjustin.controller.config.KeyMappingConfiguration;
import de.einsjustin.controller.event.ControllerStickEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.event.Subscribe;

public class ControllerStickListener {

  private final static float SPEED = 8.0f;
  private final KeyMappingConfiguration config;
  private final CameraHandler cameraHandler = ControllerAddon.references().cameraHandler();

  public ControllerStickListener(ControllerAddon addon) {
    this.config = addon.configuration().keyMappingConfiguration();
  }

  @Subscribe
  public void onStickMove(ControllerStickEvent event) {
    if (!Laby.labyAPI().minecraft().minecraftWindow().isFocused()
        || Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened()) {
      return;
    }
    ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
    if (player == null) {
      return;
    }

    float moveX = this.applyDeadzoneLeft(event.moveX());
    float moveY = this.applyDeadzoneLeft(event.moveY());
    float lookX = this.applyDeadzoneRight(event.lookX());
    float lookY = this.applyDeadzoneRight(event.lookY());

    double sensitivity = this.config.sensitivity().get() / 10f * 0.6f + 0.2f;
    double adjustment = Math.pow(sensitivity, 3) * SPEED;

    if (lookX != 0.0f || lookY != 0.0f) {
      this.cameraHandler.turn(lookX * adjustment, lookY * adjustment);
    }

    MinecraftInputMapping forward = this.getMapping("key.forward");
    MinecraftInputMapping left = this.getMapping("key.left");
    MinecraftInputMapping back = this.getMapping("key.back");
    MinecraftInputMapping right = this.getMapping("key.right");

    // TODO: Fix keyboard movement
    if (moveX != 0f) {
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

    if (moveY != 0f) {
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

  private float applyDeadzoneLeft(float value) {
    return Math.abs(value) < this.config.deadzoneLeft().get() / 10f ? 0 : value;
  }

  private float applyDeadzoneRight(float value) {
    return Math.abs(value) < this.config.deadzoneRight().get() / 10f ? 0 : value;
  }
}
