package ninja.bytecode.shuriken.bukkit.event;

import lombok.Data;
import lombok.Getter;
import org.bukkit.event.HandlerList;

public class ShurikenPacketEvent extends ShurikenCancellableEvent {
    @Getter
    private final Object rawPacket;
    private static final HandlerList handlers = new HandlerList();

    public ShurikenPacketEvent(Object rawPacket)
    {
        super();
        this.rawPacket = rawPacket;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
