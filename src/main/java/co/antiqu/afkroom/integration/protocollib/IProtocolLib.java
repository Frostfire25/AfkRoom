package co.antiqu.afkroom.integration.protocollib;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.integration.Integration;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import org.bukkit.Bukkit;

public class IProtocolLib extends Integration {

    @Getter
    private ProtocolManager protocolManager;

    private boolean enabled;

    private AfkRoom i;

    public IProtocolLib(AfkRoom i) {
        super("ProtocolLib");
        this.i = i;
        setSoftDepend(false);
        enabled = isEnabled();

        if(!enabled) {
            Bukkit.getPluginManager().disablePlugin(i);
            return;
        }

        protocolManager = ProtocolLibrary.getProtocolManager();
    }


}
