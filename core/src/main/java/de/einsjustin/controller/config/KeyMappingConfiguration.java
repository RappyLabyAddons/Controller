package de.einsjustin.controller.config;

import de.einsjustin.controller.ui.widgets.ControllerKeyBindWidget.ControllerKeyBindSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class KeyMappingConfiguration extends Config {

  @ControllerKeyBindSetting("key.jump")
  private ConfigProperty<Integer> jump = new ConfigProperty<>(-1);

  @ControllerKeyBindSetting("key.forward")
  private ConfigProperty<Integer> forward = new ConfigProperty<>(-1);

  @ControllerKeyBindSetting("key.back")
  private ConfigProperty<Integer> backward = new ConfigProperty<>(-1);

}
