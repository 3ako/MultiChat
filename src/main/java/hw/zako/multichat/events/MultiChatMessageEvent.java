package hw.zako.multichat.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class MultiChatMessageEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel;
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
    
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private String sender;
    private String message;

    public MultiChatMessageEvent(String sender, String message) {
        super(true);
        this.sender = sender;
        this.message = message;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
