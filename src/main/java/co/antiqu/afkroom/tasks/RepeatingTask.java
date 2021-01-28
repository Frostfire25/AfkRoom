package co.antiqu.afkroom.tasks;

import co.antiqu.afkroom.engines.Engine;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class RepeatingTask extends Engine {

    @Getter
    private JavaPlugin i;

    @Getter
    private long delayInTicks;

    @Getter
    private long startingDelay;

    @Getter
    private BukkitScheduler scheduler;

    @Getter
    private boolean async;

    public RepeatingTask(JavaPlugin i) {
        super(i);
        this.i = i;
        this.delayInTicks = 0;
        this.startingDelay = 0;
        this.async = false;
        this.scheduler = i.getServer().getScheduler();
    }

    private void init(int delayInTicks, int startingDelay, boolean async) {

        if(async) {
            scheduler.runTaskTimerAsynchronously(i, () -> {
                if(isActive()) {
                    go(System.currentTimeMillis());
                }
            }, startingDelay, delayInTicks);
        }

        else {
            scheduler.runTaskTimer(i, () -> {
                if(isActive()) {
                    go(System.currentTimeMillis());
                }
            }, startingDelay, delayInTicks);
        }
    }

    public void setDelay(int delayInTicks, int startingDelay, boolean async) {
        this.delayInTicks = delayInTicks;
        setActive(true);
        init(delayInTicks, startingDelay, async);
    }

    public abstract void go(long currenttime);

}
