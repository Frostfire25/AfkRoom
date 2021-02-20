package co.antiqu.afkroom.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashSet;

public class AbstractEvent extends Event implements Cancellable {

    public AbstractEvent() {

    }

    private HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public void run() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
