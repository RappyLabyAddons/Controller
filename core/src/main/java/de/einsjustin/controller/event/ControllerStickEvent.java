package de.einsjustin.controller.event;

import de.einsjustin.controller.api.Controller;
import net.labymod.api.event.Event;

public record ControllerStickEvent(Controller controller, float moveX, float moveY, float lookX,
                                   float lookY) implements Event {

}
