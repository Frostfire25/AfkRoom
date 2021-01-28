package co.antiqu.afkroom.integration;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

public abstract class Integration {

    @Getter
    private String name;

    @Getter @Setter
    private boolean softDepend;

    public Integration(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }

}
