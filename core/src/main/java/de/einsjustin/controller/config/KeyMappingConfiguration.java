package de.einsjustin.controller.config;

import de.einsjustin.controller.api.ControllerKey;
import de.einsjustin.controller.ui.widgets.ControllerKeyBindWidget.ControllerKeyBindSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class KeyMappingConfiguration extends Config {

  @ControllerKeyBindSetting("key.jump")
  private final ConfigProperty<Integer> jump = new ConfigProperty<>(ControllerKey.A.getButtonId());

}
