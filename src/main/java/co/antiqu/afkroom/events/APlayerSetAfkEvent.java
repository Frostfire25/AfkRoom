package co.antiqu.afkroom.events;

import co.antiqu.afkroom.objects.wrapper.APlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
public class APlayerSetAfkEvent extends AbstractEvent {

    private APlayer aplayer;

    private long timeOfAfkInMilis;

    private Location priorToAfkLocation;

}
