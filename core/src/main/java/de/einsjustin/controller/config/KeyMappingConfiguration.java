package de.einsjustin.controller.config;

import de.einsjustin.controller.ui.widgets.ControllerKeyBindWidget.ControllerKeyBindSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class KeyMappingConfiguration extends Config {

  @ControllerKeyBindSetting
  private ConfigProperty<Integer> jump = new ConfigProperty<>(-1);
}
