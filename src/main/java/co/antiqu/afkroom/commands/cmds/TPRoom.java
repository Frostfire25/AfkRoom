package co.antiqu.afkroom.commands.cmds;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.commands.AFKCMD;
import co.antiqu.afkroom.util.MSG;
import co.antiqu.afkroom.util.Perm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TPRoom extends AFKCMD {

    private AfkRoom i;

    public TPRoom(AfkRoom i) {
        this.i = i;

        setExtraArg(0);
        setHasToBePlayer(true);
        setIdentifiers(Arrays.asList("tproom","tp","room"));
        setPermission(Perm.ADMIN);
    }

    @Override
    public void run(CommandSender sender, String[] extraArgs) {
        Player player = (Player) sender;
        player.teleport(i.getAfkRoomManager().getLocation());
        player.sendMessage(MSG.TELEPORTED_TO_AFK_ROOM);
    }
}
