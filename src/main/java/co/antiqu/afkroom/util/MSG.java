package co.antiqu.afkroom.util;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.util.configws.Config;
import org.bukkit.configuration.file.FileConfiguration;

import javax.tools.JavaCompiler;

public class MSG {

    private AfkRoom i;

    public static String PREFIX;

    public static String NO_PERM;
    public static String INCORRECT_ARGS;
    public static String SUBCOMMAND_NOT_FOUND;
    public static String MESSAGE_TO_PLAYER;
    public static String MESSAGE_TO_SERVER;
    public static String SET_AFK_ROOM;
    public static String TELEPORTED_TO_AFK_ROOM;
    public static String TELEPORTED_TO_SPAWN;
    public static String TELEPORTED_TO_LAST_LOCATION;
    public static String FROZEN_SERVER;
    public static String DEFROZE_SERVER;

    public static boolean sendServerMessageOnAfk;
    public static boolean sendMessageToPlayerOnAfk;

    public static boolean spawnAtSpawn;

    public static boolean enablePacketSaving;

    public static boolean unAfkPlayersOnDisable;

    private static final FileConfiguration config = AfkRoom.getInstance().getLang().getConfig();
    private static final FileConfiguration configCF = AfkRoom.getInstance().getConfig();

    public MSG(AfkRoom i) {
        this.i = i;

        sendServerMessageOnAfk = config.getBoolean("messages.sendMessageToServer");
        sendMessageToPlayerOnAfk = config.getBoolean("messages.sendMessageToPlayer");

        spawnAtSpawn = configCF.getBoolean("spawn_at_spawn");
        enablePacketSaving = configCF.getBoolean("afk_packet_hault");

        unAfkPlayersOnDisable = configCF.getBoolean("un_afk_players_on_disable");

        //System.out.println(config.getString("messages.prefix"));

        PREFIX = Color.t(config.getString("messages.prefix"));

        NO_PERM = build("nopermissions");
        INCORRECT_ARGS = build("incorrect_args");
        MESSAGE_TO_PLAYER = build("message_to_player");
        MESSAGE_TO_SERVER = build("message_to_server");
        SUBCOMMAND_NOT_FOUND = build("subcommand_not_found");
        SET_AFK_ROOM = build("afkroom_set");
        TELEPORTED_TO_AFK_ROOM = build("teleported_to_afk_room_command");
        TELEPORTED_TO_SPAWN = build("teleported_to_spawn");
        TELEPORTED_TO_LAST_LOCATION = build("teleported_to_last_location");
        FROZEN_SERVER = build("frozen_server");
        DEFROZE_SERVER = build("de_frozen_server");
    }

    private String build(String string) {
        return Color.t(config.getString("messages."+string+"").replaceAll("%pf%", PREFIX));
    }

}

