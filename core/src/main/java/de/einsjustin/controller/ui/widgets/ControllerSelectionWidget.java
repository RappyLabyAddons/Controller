package de.einsjustin.controller.ui.widgets;

import de.einsjustin.controller.ControllerAddon;
import de.einsjustin.controller.DefaultControllerHandler;
import de.einsjustin.controller.api.Controller;
import de.einsjustin.controller.api.ControllerHandler;
import net.labymod.api.Textures.SpriteCommon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@AutoWidget
@SettingWidget
@Link("controller-selection.lss")
public class ControllerSelectionWidget extends FlexibleContentWidget {

  private final ControllerHandler controllerHandler;
  private DropdownWidget<String> controllerDropdown;

  public ControllerSelectionWidget() {
    this.controllerHandler = ControllerAddon.references().controllerHandler();
  }

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);

    this.controllerDropdown = new DropdownWidget<>();
    this.controllerDropdown.addId("controller-dropdown");
    this.controllerDropdown.setChangeListener(controllerName -> {
      this.controllerHandler.setSelectedController(controllerName);
    });
    this.addFlexibleContent(this.controllerDropdown);

    ButtonWidget refreshButton = ButtonWidget.icon(SpriteCommon.REFRESH);
    refreshButton.addId("refresh-button");
    this.setPressable(this::updateDropdown);
    this.addContent(refreshButton);

    updateDropdown();
  }

  private void updateDropdown() {
    this.controllerHandler.updateControllerList();
    this.controllerDropdown.clear();
    List<Controller> controllers = this.controllerHandler.getControllers();
    if (controllers.isEmpty()) {
      this.controllerDropdown.add("No Controller connected");
      this.controllerDropdown.setSelected("No Controller connected");
      return;
    }
    for (Controller controller : controllers) {
      this.controllerDropdown.add(controller.name());
    }
    this.controllerDropdown.setSelected(controllers.getFirst().name());
  }

  @SettingFactory
  public static class Factory implements WidgetFactory<ControllerSelectionSetting, ControllerSelectionWidget> {

    @Override
    public Class<?>[] types() {
      return new Class[0];
    }

    @Override
    public ControllerSelectionWidget[] create(Setting setting, ControllerSelectionSetting annotation, SettingInfo<?> info, SettingAccessor accessor) {
      return new ControllerSelectionWidget[] {
          new ControllerSelectionWidget()
      };
    }
  }

  @SettingElement(extended = true)
  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface ControllerSelectionSetting {
  }
}
