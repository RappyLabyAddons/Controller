package de.einsjustin.controller.api.model;

import net.labymod.api.util.I18n;
import net.labymod.api.util.StringUtil;
import org.lwjgl.glfw.GLFW;

public enum ControllerKey {
  NONE(-1, MappingType.NONE),
  A(GLFW.GLFW_GAMEPAD_BUTTON_A, MappingType.BUTTON),
  B(GLFW.GLFW_GAMEPAD_BUTTON_B, MappingType.BUTTON),
  X(GLFW.GLFW_GAMEPAD_BUTTON_X, MappingType.BUTTON),
  Y(GLFW.GLFW_GAMEPAD_BUTTON_Y, MappingType.BUTTON),
  LEFT_BUMPER(GLFW.GLFW_GAMEPAD_BUTTON_LEFT_BUMPER, MappingType.BUMPER, ButtonSide.LEFT),
  RIGHT_BUMPER(GLFW.GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER, MappingType.BUMPER, ButtonSide.RIGHT),
  LEFT_TRIGGER(GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER, MappingType.TRIGGER, ButtonSide.LEFT),
  RIGHT_TRIGGER(GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER, MappingType.TRIGGER, ButtonSide.RIGHT),
  BACK(GLFW.GLFW_GAMEPAD_BUTTON_BACK, MappingType.BUTTON),
  START(GLFW.GLFW_GAMEPAD_BUTTON_START, MappingType.BUTTON),
  LEFT_STICK(GLFW.GLFW_GAMEPAD_BUTTON_LEFT_THUMB, MappingType.STICK, ButtonSide.LEFT),
  RIGHT_STICK(GLFW.GLFW_GAMEPAD_BUTTON_RIGHT_THUMB, MappingType.STICK, ButtonSide.RIGHT),
  DPAD_UP(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP, MappingType.DPAD, ButtonSide.UP),
  DPAD_RIGHT(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT, MappingType.DPAD, ButtonSide.RIGHT),
  DPAD_DOWN(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN, MappingType.DPAD, ButtonSide.DOWN),
  DPAD_LEFT(GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT, MappingType.DPAD, ButtonSide.LEFT);

  private final int buttonId;
  private final MappingType type;
  private final ButtonSide side;

  ControllerKey(int buttonId, MappingType type) {
    this(buttonId, type, ButtonSide.NONE);
  }

  ControllerKey(int buttonId, MappingType type, ButtonSide side) {
    this.buttonId = buttonId;
    this.type = type;
    this.side = side;
  }

  public int getButtonId() {
    return this.buttonId;
  }

  public MappingType getType() {
    return this.type;
  }

  public ButtonSide getSide() {
    return this.side;
  }

  public String getLabel() {
    return this.side != ButtonSide.NONE
        ? I18n.translate("controller.buttonSide." + this.side.name().toLowerCase())
        : StringUtil.capitalizeWords(this.name());
  }

  public static ControllerKey getKey(int buttonId, boolean trigger) {
    for (ControllerKey key : ControllerKey.values()) {
      if (key.getButtonId() == buttonId && (!trigger || key.getType() == MappingType.TRIGGER)) {
        return key;
      }
    }
    return NONE;
  }

  public enum ButtonSide {
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT
  }

  public enum MappingType {
    NONE,
    BUTTON,
    STICK,
    DPAD,
    BUMPER,
    TRIGGER
  }
}
