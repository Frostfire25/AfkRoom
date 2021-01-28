package co.antiqu.afkroom.commands;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.commands.cmds.FreezeAfk;
import co.antiqu.afkroom.commands.cmds.SetRoom;
import co.antiqu.afkroom.commands.cmds.TPRoom;
import co.antiqu.afkroom.util.Color;
import co.antiqu.afkroom.util.MSG;
import co.antiqu.afkroom.util.Perm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandManager implements CommandExecutor, TabCompleter {

    private AfkRoom i;

    //Identifier , AFKCMD
    public Set<AFKCMD> commands;

    public CommandManager(AfkRoom i) {
        this.i = i;

        i.getCommand("afkroom").setExecutor(this);
        i.getCommand("afkroom").setTabCompleter(this);

        commands = new HashSet<>();

        commands.add(new SetRoom(i));
        commands.add(new TPRoom(i));
        commands.add(new FreezeAfk(i));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length <= 0) {
            if(sender.hasPermission(Perm.ADMIN.getPerm()))
                sender.sendMessage(Color.t(
                        "&c<-------- < &6AfkRoom Help &c > --------->\n" +
                           "&c/afkroom setroom\n" +
                           "&c/afkroom tproom\n" +
                           "c/afkroom freeze\n"));
            return true;
        }

        for(AFKCMD n : commands) {
            for(String m : n.getIdentifiers()) {
                if(m.equalsIgnoreCase(args[0])) {
                    if(n.isHasToBePlayer() && !(sender instanceof Player)) {
                        return false;
                    }

                    if(!sender.hasPermission(n.getPermission().getPerm())) {
                        sender.sendMessage(MSG.NO_PERM);
                        return false;
                    }

                    if((args.length-1) != n.getExtraArg()) {
                        sender.sendMessage(MSG.INCORRECT_ARGS);
                        return false;
                    }

                    String[] editedArgs = null;
                    if(args.length > 1) {
                        editedArgs = new String[args.length-1];
                        for(int i = 1; i < args.length; i++) {
                            editedArgs[i-1] = args[i];
                        }
                    }

                    n.run(sender,editedArgs);
                    return true;
                }
            }
        }
        sender.sendMessage(MSG.SUBCOMMAND_NOT_FOUND);
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
