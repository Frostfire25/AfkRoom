package co.antiqu.afkroom.objects;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.objects.wrapper.APlayer;
import co.antiqu.afkroom.tasks.Freezable;
import co.antiqu.afkroom.tasks.RepeatingTask;
import co.antiqu.afkroom.util.MSG;
import co.antiqu.afkroom.util.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class AfkManager extends RepeatingTask implements Freezable {

    private AfkRoom i;

    //Player . Last Moved Time
    private Map<APlayer, Long> afkingMap;

    @Getter
    private boolean Frozen;

    @Getter
    private int AFK_SECCONDS;

    public AfkManager(AfkRoom i) {
        super(i);
        this.i = i;
        init();
    }

    private void init() {
        Frozen = false;
        AFK_SECCONDS = i.getConfig().getInt("afk_time");
        afkingMap = new HashMap<>();
        remove = new HashSet<>();
        setDelay(20,70,true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent evt) {
        System.out.println("here");
        if(isFrozen())
            return;
        if (evt.getFrom().getBlockX() == evt.getTo().getBlockX() && evt.getFrom().getBlockZ() == evt.getTo().getBlockZ() && evt.getFrom().getBlockY() == evt.getTo().getBlockY()) {
            return;
        }

        //System.out.println("moved one block");

        Player player = evt.getPlayer();
        APlayer aPlayer = i.getAPlayerManager().getAPlayer(player);

        if(aPlayer == null) {
            System.out.println("[AfkRoom] issue finding APlayer, in onMove");
            return;
        }

        if(aPlayer.isAfk() /*|| player.getWorld().getName().equalsIgnoreCase(i.getAfkRoomManager().getLocation().getWorld().getName())*/) {
            long mili = aPlayer.getAfkSince();
            aPlayer.setAfk(false);
            Location loc = null;
            if(MSG.spawnAtSpawn) {
               aPlayer.getPlayer().sendMessage(MSG.TELEPORTED_TO_SPAWN.replaceAll("%time%", (TimeUtil.formatPlayTime(System.currentTimeMillis()-mili))));
               loc = i.getAfkRoomManager().getWorld().getSpawnLocation();
            } else {
                aPlayer.getPlayer().sendMessage(MSG.TELEPORTED_TO_LAST_LOCATION.replaceAll("%time%", (TimeUtil.formatPlayTime(System.currentTimeMillis()-mili))));
                loc = aPlayer.getLastKnownLocation();
            }
            aPlayer.getPlayer().teleport(loc);
            return;

        } else {

            aPlayer.setLastKnownLocation(player.getLocation());
            afkingMap.remove(aPlayer);
            afkingMap.put(aPlayer,System.currentTimeMillis());

        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent evt) {
        Player player = evt.getPlayer();
        APlayer aPlayer = i.getAPlayerManager().getAPlayer(player);

        if(aPlayer == null) {
            System.out.println("[AfkRoom] issue finding APlayer, in onMove");
            return;
        }
        afkingMap.remove(aPlayer);
    }

    protected transient HashSet<APlayer> remove;

    @Override
    public void go(long currenttime) {
        if(isFrozen())
            return;

        for(APlayer n : afkingMap.keySet()) {
            if(currenttime > (afkingMap.get(n) + AFK_SECCONDS*1000)) {
                setAfk(n);
            }
        }

        getScheduler().scheduleSyncDelayedTask(i,() -> {
            remove.forEach(n -> {
                afkingMap.remove(n);
            });
            remove.clear();
        });
    }

    public void setAfk(APlayer n) {
        getScheduler().scheduleSyncDelayedTask(i, () -> {

            if(n.isAfk())
                return;

            //Making the player go afk
            //System.out.println("removing player added ");
            remove.add(n);
            n.setAfk(true);

            Player player = n.getPlayer();

            if(player == null) {
                System.out.println("[AfkRoom] issue finding Player, task in AfkManager");
                return;
            }

            player.teleport(i.getAfkRoomManager().getLocation());

            if(MSG.sendMessageToPlayerOnAfk) {
                player.sendMessage(MSG.MESSAGE_TO_PLAYER);
            }

            if(MSG.sendServerMessageOnAfk) {
                for(Player m : Bukkit.getOnlinePlayers()) {
                    if(m.getUniqueId().toString().equalsIgnoreCase(n.getUuid().toString()))
                        continue;
                    m.sendMessage(MSG.MESSAGE_TO_SERVER.replaceAll("%player%", player.getName()));
                }
            }
        });
    }

    @Override
    public void setFrozen(boolean val) {
        Frozen = val;
        afkingMap.clear();
    }
}
