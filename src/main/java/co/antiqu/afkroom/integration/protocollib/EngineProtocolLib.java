package co.antiqu.afkroom.integration.protocollib;

import co.antiqu.afkroom.AfkRoom;
import co.antiqu.afkroom.engines.Engine;
import co.antiqu.afkroom.objects.wrapper.APlayer;
import co.antiqu.afkroom.util.MSG;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class EngineProtocolLib extends Engine {

    private AfkRoom i;

    public EngineProtocolLib(AfkRoom i) {
        super(i);
        this.i = i;
        Bukkit.getPluginManager().registerEvents(this,i);
        if(!MSG.enablePacketSaving)
            return;
        init();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent evt) {
        if(i.getAPlayerManager().getAPlayer(evt.getPlayer()).isAfk())
            evt.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent evt) {
        if(i.getAPlayerManager().getAPlayer(evt.getPlayer()).isAfk())
            evt.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(EntityDamageByEntityEvent evt) {
        if(!(evt.getDamager() instanceof Player))
            return;
        if(i.getAPlayerManager().getAPlayer(((Player) evt.getDamager())).isAfk())
            evt.setCancelled(true);
    }

    private void init() {
        if(!i.getIProtocolLib().isEnabled())
            return;

        if(!MSG.enablePacketSaving)
            return;

        System.out.println("[AfkRoom] - Unregistering Non-Version Packets. ");

        PacketType[] playClient = { PacketType.Play.Client.TAB_COMPLETE, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CLOSE_WINDOW,
                PacketType.Play.Client.CUSTOM_PAYLOAD, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.ABILITIES, PacketType.Play.Client.BLOCK_DIG,
                PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.HELD_ITEM_SLOT, PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.BLOCK_PLACE };

        PacketType[] playServer = { PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, PacketType.Play.Server.SPAWN_ENTITY_WEATHER, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.SPAWN_ENTITY_PAINTING,
                PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.ANIMATION, PacketType.Play.Server.BLOCK_BREAK_ANIMATION, PacketType.Play.Server.BLOCK_ACTION,
                PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.BLOCK_CHANGE, PacketType.Play.Server.MULTI_BLOCK_CHANGE, PacketType.Play.Server.ENTITY_STATUS,
                PacketType.Play.Server.WORLD_EVENT, PacketType.Play.Server.WORLD_PARTICLES, PacketType.Play.Server.ENTITY, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK,
                PacketType.Play.Server.ENTITY_LOOK, PacketType.Play.Server.ENTITY_HEAD_ROTATION, PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.ENTITY_VELOCITY,
                PacketType.Play.Server.ENTITY_EQUIPMENT, PacketType.Play.Server.NAMED_SOUND_EFFECT, PacketType.Play.Server.ENTITY_TELEPORT, PacketType.Play.Server.ENTITY_EFFECT,
                PacketType.Play.Server.BLOCK_BREAK };


        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(i, playClient) {
                    @Override
                    public void onPacketReceiving(PacketEvent evt) {
                        if (evt.getPlayer() == null)
                            return;

                        APlayer aPlayer = i.getAPlayerManager().getAPlayer(evt.getPlayer());
                        if (aPlayer.isAfk())
                            evt.setCancelled(true);
                    }
                });

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(i, playServer) {
                    @Override
                    public void onPacketSending(PacketEvent evt) {
                        if(evt.getPlayer() == null)
                            return;

                        APlayer aPlayer = i.getAPlayerManager().getAPlayer(evt.getPlayer());
                        if(aPlayer.isAfk())
                            evt.setCancelled(true);
                    }
                });
    }

    /*
    PacketType[] playMove = { PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.FLYING };

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(i, playMove) {
                    @Override
                    public void onPacketReceiving(PacketEvent evt) {
                        if(evt.getPlayer() == null)
                            return;

                        APlayer aPlayer = i.getAPlayerManager().getAPlayer(evt.getPlayer());
                        if(aPlayer.isAfk()) {
                            evt.setCancelled(true);
                        }
                    }
        });
     */
}
