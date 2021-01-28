package co.antiqu.afkroom.objects.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class APlayer {

    @Getter @Setter
    private Location lastKnownLocation;

    @Getter
    private Player player;

    @Getter
    private UUID uuid;

    @Getter
    public boolean afk;

    @Getter
    private long afkSince;

    public APlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.lastKnownLocation = player.getLocation();
        this.afk = false;
        this.afkSince = -1;
    }

    public void setAfk(boolean val) {
        if(val) {
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
