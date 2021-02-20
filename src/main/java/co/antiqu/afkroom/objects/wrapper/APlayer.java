package co.antiqu.afkroom.objects.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class APlayer {

    private Location lastKnownLocation;

    private Player player;

    private UUID uuid;

    public boolean afk;

    private long afkSince;

    public APlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.lastKnownLocation = player.getLocation();
        setTimeStamp(false, -1);
    }

    public void setAfk(boolean val) {
        setTimeStamp(val, val ? System.currentTimeMillis() : -1);
    }

    public void setTimeStamp(boolean value, long timestamp) {
        if(value) {
            afk = true;
            afkSince = System.currentTimeMillis();
        } else {
            afk = false;
            afkSince = -1;
        }
    }

    public boolean isOnline() {
        if(player == null || !player.isOnline())
            return false;
        return true;
    }

    public Player getBukkitPlayer() {
        return isOnline() ? player : null;
    }

}
