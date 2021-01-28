package co.antiqu.afkroom.commands.cmds;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.commands.AFKCMD;
import co.antiqu.afkroom.util.MSG;
import co.antiqu.afkroom.util.Perm;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetRoom extends AFKCMD {

    private AfkRoom i;

    public SetRoom(AfkRoom i) {
        this.i = i;

        setHasToBePlayer(true);
        setIdentifiers(Arrays.asList("setroom"));
        setExtraArg(0);
        setPermission(Perm.ADMIN);
    }

    @Override
    public void run(CommandSender sender, String[] extraArgs) {
        Player player = (Player) sender;
        i.getAfkRoomManager().updateTpLocation(player.getLocation());
        player.sendMessage(MSG.SET_AFK_ROOM);
    }
}
