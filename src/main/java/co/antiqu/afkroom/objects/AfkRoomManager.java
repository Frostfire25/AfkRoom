package co.antiqu.afkroom.objects;

import co.antiqu.afkroom.AfkRoom;
import com.tchristofferson.configupdater.ConfigUpdater;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Arrays;

public class AfkRoomManager {

    private AfkRoom i;

    private FileConfiguration config;

    @Getter
    private Location location;

    @Getter
    private World world;

    public AfkRoomManager(AfkRoom i) {
        this.i = i;
        config = i.getConfig();
        configFile = new File(i.getDataFolder(), "config.yml");
        init();
    }

    private void init() {
        try {
            location = new Location(Bukkit.getWorld(config.getString("room.world")),
                    config.getInt("room.x"),
                    config.getInt("room.y"),
                    config.getInt("room.z"),
                    config.getInt("room.yaw"),
                    config.getInt("room.pitch")
                    );
            this.world = Bukkit.getWorld(config.getString("default_world"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTpLocation(Location loc) {
        this.location = loc;
        config.set("room.world", loc.getWorld().getName());
        config.set("room.x", loc.getBlockX());
        config.set("room.y", loc.getBlockY());
        config.set("room.z", loc.getBlockZ());
        config.set("room.yaw", loc.getYaw());
        config.set("room.pitch", loc.getPitch());
        saveConfig();
    }

    private File configFile;

    public void saveConfig() {
        try {
            i.saveConfig();
            ConfigUpdater.update(i,"config.yml", configFile, Arrays.asList());
            i.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
