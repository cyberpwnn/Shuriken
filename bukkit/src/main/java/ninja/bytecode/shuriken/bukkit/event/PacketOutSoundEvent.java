package ninja.bytecode.shuriken.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;

public class PacketOutSoundEvent extends ShurikenPacketEvent {
    @Getter
    @Setter
    private Sound sound;

    @Getter
    @Setter
    private Entity entity;

    @Getter
    @Setter
    private float volume;

    @Getter
    @Setter
    private float pitch;

    @Getter
    @Setter
    private SoundCategory category;

    public PacketOutSoundEvent(Object rawPacket, Sound sound, Entity entity, float volume, float pitch, SoundCategory category)
    {
        super(rawPacket);
        this.sound = sound;
        this.entity = entity;
        this.volume = volume;
        this.pitch = pitch;
        this.category = category;
    }
}
