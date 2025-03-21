package de.einsjustin.controller;

import de.einsjustin.controller.config.ControllerConfiguration;
import de.einsjustin.controller.core.generated.DefaultReferenceStorage;
import de.einsjustin.controller.listener.ControllerStickListener;
import de.einsjustin.controller.listener.GameTickListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ControllerAddon extends LabyAddon<ControllerConfiguration> {

  private static ControllerAddon INSTANCE;

  @Override
  protected void enable() {
    INSTANCE = this;
    this.registerSettingCategory();
    this.registerListener(new GameTickListener());
    this.registerListener(new ControllerStickListener());
  }

  @Override
  protected Class<ControllerConfiguration> configurationClass() {
    return ControllerConfiguration.class;
  }

  public static DefaultReferenceStorage references() {
    return INSTANCE.referenceStorageAccessor();
  }
}
