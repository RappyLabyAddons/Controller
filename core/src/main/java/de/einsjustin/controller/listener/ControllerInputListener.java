package de.einsjustin.controller.listener;

import de.einsjustin.controller.ControllerAddon;
import de.einsjustin.controller.api.handlers.HotbarHandler;
import de.einsjustin.controller.api.model.ControllerKey;
import de.einsjustin.controller.config.KeyMappingConfiguration;
import de.einsjustin.controller.event.ControllerInputEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent.State;

public class ControllerInputListener {

  private final KeyMappingConfiguration config;
  private final HotbarHandler hotbarHandler = ControllerAddon.references().hotbarHandler();

  public ControllerInputListener(ControllerAddon addon) {
    this.config = addon.configuration().keyMappingConfiguration();
  }

  @Subscribe
  public void onInput(ControllerInputEvent event) {
    if (event.state() != State.PRESS) {
      return;
    }
    ControllerKey key = ControllerKey.getKey(event.buttonId(), event.trigger());

    // Handle buttons which aren't implemented in vanilla
    if (key.getButtonId() == this.config.pause().get()) {
      // TODO: Implement escape button
    } else if (key.getButtonId() == this.config.hotbarLeft().get()) {
      this.hotbarHandler.moveLeft();
    } else if (key.getButtonId() == this.config.hotbarRight().get()) {
      this.hotbarHandler.moveRight();
    }
  }
}
