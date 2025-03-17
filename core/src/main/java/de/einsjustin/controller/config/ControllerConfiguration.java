package de.einsjustin.controller.config;

import de.einsjustin.controller.ui.widgets.ControllerSelectionWidget.ControllerSelectionSetting;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class ControllerConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @ControllerSelectionSetting
  private final ConfigProperty<String> controllerSelection = new ConfigProperty<>("");

  private final KeyMappingConfiguration keyMappingConfiguration = new KeyMappingConfiguration();

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<String> controllerSelection() {
    return controllerSelection;
  }

  public KeyMappingConfiguration keyMappingConfiguration() {
    return keyMappingConfiguration;
  }
}
