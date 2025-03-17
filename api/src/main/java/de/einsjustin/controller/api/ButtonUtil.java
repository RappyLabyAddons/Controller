package de.einsjustin.controller.api;

import net.labymod.api.util.I18n;

public class ButtonUtil {

  public static MappingType getType(int buttonId) {
    return switch (buttonId) {
      case 4, 5 -> MappingType.BUMPER;
      case 9, 10 -> MappingType.STICK;
      case 11, 12, 13, 14 -> MappingType.DPAD;
      default -> MappingType.BUTTON;
    };
  }

  public static String getLabel(int buttonId) {
    return switch (buttonId) {
      case 0 -> "A";
      case 1 -> "B";
      case 2 -> "X";
      case 3 -> "Y";
      case 4, 14 -> "LEFT";
      case 5, 12 -> "RIGHT";
      default -> I18n.translate("labymod.ui.keybind.none");
    };
  }

  public enum MappingType {
    BUTTON,
    STICK,
    DPAD,
    BUMPER,
    TRIGGER;
  }
}
