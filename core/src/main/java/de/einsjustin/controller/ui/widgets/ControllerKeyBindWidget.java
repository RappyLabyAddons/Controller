package de.einsjustin.controller.ui.widgets;

import de.einsjustin.controller.ControllerAddon;
import de.einsjustin.controller.api.model.Controller;
import de.einsjustin.controller.api.model.ControllerKey;
import de.einsjustin.controller.event.ControllerInputEvent;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.I18n;

@AutoWidget
@SettingWidget
@Link("keybind.lss")
public class ControllerKeyBindWidget extends TextFieldWidget {

  private final Selectable<Integer> selectable;
  private final String key;
  private int buttonId;
  private boolean trigger;

  private boolean listening = false;
  private String lastVisibleText;

  public ControllerKeyBindWidget(Selectable<Integer> selectable, String key) {
    this.selectable = selectable;
    this.key = key;
    this.labyAPI.eventBus().registerListener(this);
  }

  @Subscribe
  public void onControllerInput(ControllerInputEvent event) {
    if (!this.isListening()) {
      Minecraft minecraft = this.labyAPI.minecraft();
      if (minecraft.minecraftWindow().isScreenOpened()) return;
      if (event.buttonId() != this.buttonId) {
        return;
      }

      MinecraftInputMapping inputMapping = minecraft.options().getInputMapping(this.key);

      switch (event.state()) {
        case PRESS -> inputMapping.press();
        case UNPRESSED -> inputMapping.unpress();
      }
      return;
    }

    Controller controller = event.controller();
    Controller selectedController = ControllerAddon.references().controllerHandler().getSelectedController();

    if (selectedController != controller) return;

    this.trigger = event.trigger();
    this.updateKey(event.buttonId());
  }

  @Override
  public void tick() {
    if (this.listening && !this.isFocused()) {
      this.listening = false;
    }
  }

  @Override
  public boolean keyPressed(Key key, InputType type) {
    if (this.isListening() && key == Key.ESCAPE) {
      return this.updateKey(-1);
    }
    return false;
  }

  @Override
  public boolean mouseClicked(MutableMouse mouse, MouseButton button) {
    if (button == MouseButton.LEFT) {
      this.listening = this.isHovered();
    }
    return super.mouseClicked(mouse, button);
  }

  @Override
  public boolean shouldHandleEscape() {
    return this.isListening();
  }

  @Override
  public boolean isCursorVisible() {
    return false;
  }

  @Override
  public String getVisibleText() {
    String visibleText = super.getVisibleText();
    if (this.lastVisibleText == null) {
      this.lastVisibleText = this.getFormattedText();
    }

    if (!this.lastVisibleText.equals(visibleText)) {
      String formattedText = this.getFormattedText();
      if (visibleText.equals(formattedText)) {
        this.setHoverComponent(null);
      } else {
        this.setHoverComponent(Component.text(formattedText));
      }
    }

    return visibleText;
  }

  @Override
  protected String getFormattedText() {
    if (this.buttonId == -1) {
      return I18n.translate("labymod.ui.keybind.none");
    }
    ControllerKey key = ControllerKey.getKey(this.buttonId, this.trigger);
    return String.format(
        "%s %s",
        I18n.translate("controller.mappingType." + key.getType().name().toLowerCase()),
        key.getLabel()
    );
  }

  private boolean updateKey(int buttonId) {
    this.buttonId = buttonId;
    this.selectable.select(this.buttonId);
    this.setFocused(false);
    this.listening = false;
    return true;
  }

  public boolean isListening() {
    return this.listening;
  }

  @Override
  public boolean shouldDisplayPlaceHolder() {
    return this.isListening();
  }

  @Override
  public void destroy() {
    super.destroy();
    this.labyAPI.eventBus().unregisterListener(this);
  }

  @SettingFactory
  public static class Factory implements WidgetFactory<ControllerKeyBindSetting, ControllerKeyBindWidget> {

    @Override
    public Class<?>[] types() {
      return new Class[]{Integer.class};
    }

    @Override
    public ControllerKeyBindWidget[] create(Setting setting, ControllerKeyBindSetting annotation, SettingInfo<?> info, SettingAccessor accessor) {

      ControllerKeyBindWidget widget = new ControllerKeyBindWidget(accessor::set, annotation.value());
      widget.buttonId = accessor.get();
      widget.placeholderTranslatable = "controller.settings.controllerKeyBind.placeholder";

      accessor.property().addChangeListener(
          (t, oldValue, newValue) -> widget.updateKey(
              newValue instanceof Integer value ? value : -1)
      );

      return new ControllerKeyBindWidget[]{widget};
    }
  }

  @SettingElement
  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface ControllerKeyBindSetting {
    String value();
  }
}
