package co.antiqu.afkroom.commands;

import co.antiqu.afkroom.util.Perm;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class AFKCMD {

    @Getter @Setter
    public List<String> identifiers;

    @Getter @Setter
    public boolean hasToBePlayer;

    @Getter @Setter
    public Perm permission;

    @Getter @Setter
    private int extraArg;

    public abstract void run(CommandSender sender, String[] extraArgs);

}
