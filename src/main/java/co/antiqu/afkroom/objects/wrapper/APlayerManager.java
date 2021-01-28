package co.antiqu.afkroom.objects.wrapper;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.engines.Engine;
import co.antiqu.afkroom.objects.wrapper.APlayer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;

public class APlayerManager extends Engine {

    private AfkRoom i;

    @Getter
    private HashSet<APlayer> aPlayers;

    public APlayerManager(AfkRoom i) {
        super(i);
        this.i = i;
        this.aPlayers = new HashSet<>();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        APlayer aPlayer = contains(evt.getPlayer());
        if(aPlayer == null)
            return;
        removePlayer(aPlayer);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        APlayer aPlayer = contains(evt.getPlayer());
        if(aPlayer != null) {
            removePlayer(aPlayer);
        }
        APlayer addingPlayer = new APlayer(evt.getPlayer());
        addPlayer(addingPlayer);
        System.out.println("added aplayer " + addingPlayer.getPlayer().getName());
    }

    public APlayer contains(Player player) {
        return aPlayers.stream().filter(n -> n.getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString())).findFirst().orElse(null);
    }


    public void addPlayer(APlayer player) {
        if(!contains(player))
            aPlayers.add(player);
    }

    public boolean contains(APlayer player) {
       return aPlayers.contains(player);
    }

    public void removePlayer(APlayer player) {
        if(aPlayers.contains(player))
            aPlayers.add(player);
    }

    public APlayer getAPlayer(Player player) {
        return contains(player);
    }

    public void updateLocation(APlayer aPlayer) {
        Player player = aPlayer.getBukkitPlayer();
        if(player != null) {
            aPlayer.setLastKnownLocation(player.getLocation());
        }
    }

}
