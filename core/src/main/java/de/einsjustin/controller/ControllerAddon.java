package de.einsjustin.controller;

import de.einsjustin.controller.config.ControllerConfiguration;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ControllerAddon extends LabyAddon<ControllerConfiguration> {

  private static ControllerAddon INSTANCE;

  @Override
  protected void enable() {
    INSTANCE = this;
    this.registerSettingCategory();
  }

  @Override
  protected Class<ControllerConfiguration> configurationClass() {
    return ControllerConfiguration.class;
  }

  public static DefaultReferenceStorage references() {
    return INSTANCE.referenceStorageAccessor();
  }
}
