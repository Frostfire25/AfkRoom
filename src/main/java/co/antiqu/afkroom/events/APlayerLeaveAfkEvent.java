package co.antiqu.afkroom.events;

import co.antiqu.afkroom.objects.wrapper.APlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
public class APlayerLeaveAfkEvent extends AbstractEvent {

    private APlayer aPlayer;

    private long timeOfUnAfkInMilis;

    /*
     Determining on SPAWNN_AT_SPAWN in config player could return to spawn rather than last known location
     */

    private Location locationPriorToAfk;

    private long amountOfTimeAfkMilis;

}
