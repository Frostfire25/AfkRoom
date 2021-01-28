package co.antiqu.afkroom.engines;

import co.antiqu.afkroom.tasks.Active;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Engine implements Listener, Active {

    private boolean active = false;

    @Override
    public void setActive(boolean val) {
        this.active = val;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Engine(JavaPlugin i) {
        Bukkit.getServer().getPluginManager().registerEvents(this,i);
    }
}
