package co.antiqu.afkroom;

import co.antiqu.afkroom.commands.CommandManager;
import co.antiqu.afkroom.integration.protocollib.EngineProtocolLib;
import co.antiqu.afkroom.integration.protocollib.IProtocolLib;
import co.antiqu.afkroom.objects.wrapper.APlayerManager;
import co.antiqu.afkroom.objects.AfkManager;
import co.antiqu.afkroom.objects.AfkRoomManager;
import co.antiqu.afkroom.util.MSG;
import co.antiqu.afkroom.util.configws.Config;
import co.antiqu.afkroom.util.configws.WSClass;
import co.antiqu.afkroom.util.configws.WSM;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AfkRoom extends JavaPlugin implements WSClass {

    @Getter
    private static AfkRoom instance;

    @Getter
    private WSM WSM;

    @Getter
    private Config lang;

    @Getter
    private MSG msg;

    @Getter
    private IProtocolLib iProtocolLib;

    @Getter
    private CommandManager commandManager;

    @Getter
    private AfkRoomManager afkRoomManager;

    @Getter
    private AfkManager afkManager;

    @Getter
    private APlayerManager aPlayerManager;

    @Getter
    private EngineProtocolLib engineProtocolLib;

    @Getter
    private Metrics metrics;

    @Override
    public void onEnable() {
        instance = this;
        //WSM = new WSM(this,this,true);
        //metrics = new Metrics(this, 9619);
        integrations();
        config();
        listeners();
        commands();
        objects();
    }

    @Override
    public void onDisable() {
        if(MSG.unAfkPlayersOnDisable) {

            //Need to set everyone afk to teleport back to their old locations
            Bukkit.getOnlinePlayers().stream().map(n -> aPlayerManager.getAPlayer(n)).filter(Objects::nonNull).forEach(n -> {
                if (n.isAfk()) {
                    afkManager.setUnAfk(n, false);
                }
            });
        }
    }

    private void config() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        lang = new Config(this,"lang.yml");
        lang.saveDefaultConfig(this);
        msg = new MSG(this);
    }

    private void listeners() {
        engineProtocolLib = new EngineProtocolLib(this);
    }

    private void commands() {
        commandManager = new CommandManager(this);
    }

    private void objects() {
        afkRoomManager = new AfkRoomManager(this);
        afkManager = new AfkManager(this);
        aPlayerManager = new APlayerManager(this);
    }

    private void integrations() {
        iProtocolLib = new IProtocolLib(this);
    }

    @Override
    public void updateConfig() {
        //Bukkit.getPluginManager().disablePlugin(this);
        //Bukkit.getPluginManager().enablePlugin(this);
    }

}
