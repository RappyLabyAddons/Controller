package de.einsjustin.controller.event;

import de.einsjustin.controller.api.model.Controller;
import net.labymod.api.event.Event;
import net.labymod.api.event.client.input.KeyEvent.State;

public record ControllerInputEvent(Controller controller, int buttonId, State state, boolean trigger) implements Event {
}
