package co.antiqu.afkroom.commands.cmds;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.commands.AFKCMD;
import co.antiqu.afkroom.util.MSG;
import co.antiqu.afkroom.util.Perm;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class FreezeAfk extends AFKCMD {

    private AfkRoom i;

    public FreezeAfk(AfkRoom i) {
        this.i = i;

        setExtraArg(0);
        setHasToBePlayer(false);
        setPermission(Perm.ADMIN);
        setIdentifiers(Arrays.asList("freeze","fz"));
    }

    @Override
    public void run(CommandSender sender, String[] extraArgs) {
        if(i.getAfkManager().isFrozen()) {
            i.getAfkManager().setFrozen(false);
            sender.sendMessage(MSG.DEFROZE_SERVER);
        } else {
            i.getAfkManager().setFrozen(true);
            sender.sendMessage(MSG.FROZEN_SERVER);
        }
    }
}
