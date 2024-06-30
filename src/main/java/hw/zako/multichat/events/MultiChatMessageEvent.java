package hw.zako.multichat.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class MultiChatMessageEvent extends Event implements Cancellable {

    private String sender;
    private String message;

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    public MultiChatMessageEvent(String sender, String message) {
        super(true);
        this.sender = sender;
        this.message = message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
