package de.einsjustin.controller.config;

import de.einsjustin.controller.api.model.ControllerKey;
import de.einsjustin.controller.ui.widgets.ControllerKeyBindWidget.ControllerKeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

public class KeyMappingConfiguration extends Config {

  @SettingSection("sticks")
  @SliderSetting(min = 1, max = 50)
  private final ConfigProperty<Integer> sensitivity = new ConfigProperty<>(25);

  @SliderSetting(min = 1, max = 9)
  private final ConfigProperty<Integer> deadzoneLeft = new ConfigProperty<>(5);

  @SliderSetting(min = 1, max = 9)
  private final ConfigProperty<Integer> deadzoneRight = new ConfigProperty<>(2);

  @SettingSection("buttons")
  @ControllerKeyBindSetting("")
  private final ConfigProperty<Integer> pause = new ConfigProperty<>(
      ControllerKey.START.getButtonId());

  @ControllerKeyBindSetting("key.playerlist")
  private final ConfigProperty<Integer> tabList = new ConfigProperty<>(
      ControllerKey.BACK.getButtonId());

  @ControllerKeyBindSetting("key.chat")
  private final ConfigProperty<Integer> chat = new ConfigProperty<>(
      ControllerKey.DPAD_RIGHT.getButtonId());

  @ControllerKeyBindSetting("key.command")
  private final ConfigProperty<Integer> command = new ConfigProperty<>(
      ControllerKey.NONE.getButtonId());

  @ControllerKeyBindSetting("key.jump")
  private final ConfigProperty<Integer> jump = new ConfigProperty<>(ControllerKey.A.getButtonId());

  @ControllerKeyBindSetting("key.sneak")
  private final ConfigProperty<Integer> sneak = new ConfigProperty<>(ControllerKey.B.getButtonId());

  @ControllerKeyBindSetting("key.sprint")
  private final ConfigProperty<Integer> sprint = new ConfigProperty<>(
      ControllerKey.LEFT_STICK.getButtonId());

  @ControllerKeyBindSetting("key.attack")
  private final ConfigProperty<Integer> attack = new ConfigProperty<>(
      ControllerKey.RIGHT_TRIGGER.getButtonId());

  @ControllerKeyBindSetting("key.use")
  private final ConfigProperty<Integer> placeBlock = new ConfigProperty<>(
      ControllerKey.LEFT_TRIGGER.getButtonId());

  @ControllerKeyBindSetting("key.pickItem")
  private final ConfigProperty<Integer> pickBlock = new ConfigProperty<>(
      ControllerKey.NONE.getButtonId());

  @ControllerKeyBindSetting("key.pickItem")
  private final ConfigProperty<Integer> dropItem = new ConfigProperty<>(
      ControllerKey.DPAD_DOWN.getButtonId());

  @ControllerKeyBindSetting("")
  private final ConfigProperty<Integer> hotbarLeft = new ConfigProperty<>(
      ControllerKey.LEFT_BUMPER.getButtonId());

  @ControllerKeyBindSetting("")
  private final ConfigProperty<Integer> hotbarRight = new ConfigProperty<>(
      ControllerKey.RIGHT_BUMPER.getButtonId());

  @ControllerKeyBindSetting("key.inventory")
  private final ConfigProperty<Integer> inventory = new ConfigProperty<>(
      ControllerKey.X.getButtonId()); // Fix inventory

  @ControllerKeyBindSetting("key.swapHands")
  private final ConfigProperty<Integer> swapHands = new ConfigProperty<>(
      ControllerKey.DPAD_LEFT.getButtonId());

  @ControllerKeyBindSetting("key.screenshot")
  private final ConfigProperty<Integer> screenshot = new ConfigProperty<>(
      ControllerKey.NONE.getButtonId());

  @ControllerKeyBindSetting("key.togglePerspective")
  private final ConfigProperty<Integer> togglePerspective = new ConfigProperty<>(
      ControllerKey.NONE.getButtonId());

  public ConfigProperty<Integer> sensitivity() {
    return this.sensitivity;
  }

  public ConfigProperty<Integer> deadzoneLeft() {
    return this.deadzoneLeft;
  }

  public ConfigProperty<Integer> deadzoneRight() {
    return this.deadzoneRight;
  }

  public ConfigProperty<Integer> pause() {
    return this.pause;
  }

  public ConfigProperty<Integer> hotbarLeft() {
    return this.hotbarLeft;
  }

  public ConfigProperty<Integer> hotbarRight() {
    return this.hotbarRight;
  }

}
