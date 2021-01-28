package co.antiqu.afkroom.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Util
{
    public static void givePlayerItem(Player player, ItemStack item, int amount) {
        if(player.getInventory().firstEmpty() == - 1) {
            for(int i = 0; i < amount; i++) {
                player.getWorld().dropItem(player.getLocation(),item);
            }
        } else {
            for(int i = 0; i < amount; i++) {
                player.getInventory().addItem(item);
            }
        }
    }

    public static boolean parseBoolean(ItemStack item, String identifier) {
        if(!item.hasItemMeta())
            return false;
        if(!item.getItemMeta().hasLore())
            return false;
        ItemMeta itemMeta = item.getItemMeta();
        String stripped_id = stripColor(ChatColor.translateAlternateColorCodes('&', identifier));
        for(int i = 0; i < itemMeta.getLore().size(); i++) {
            if(stripColor(itemMeta.getLore().get(i)).contains(stripped_id)) {
                if(stripColor(itemMeta.getLore().get(i)).contains("Yes")) {
                    return true;
                } else if(stripColor(itemMeta.getLore().get(i)).contains("No")) {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isOutsideOfBorder(final Location loc) {
        WorldBorder wb = loc.getWorld().getWorldBorder();
        double x = loc.getX();
        double z = loc.getZ();
        double size = wb.getSize() / 2.0;
        return x >= size || -x >= size || z >= size || -z >= size;
    }


    public static String formatTime(int secs) {
        int remainder = secs % 86400;

        int days = secs / 86400;
        int hours = remainder / 3600;
        int minutes = (remainder / 60) - (hours * 60);
        int seconds = (remainder % 3600) - (minutes * 60);

        String fDays = (days > 0 ? " " + days + " day" + (days > 1 ? "s" : "") : "");
        String fHours = (hours > 0 ? " " + hours + " hour" + (hours > 1 ? "s" : "") : "");
        String fMinutes = (minutes > 0 ? " " + minutes + " minute" + (minutes > 1 ? "s" : "") : "");
        String fSeconds = (seconds > 0 ? " " + seconds + " second" + (seconds > 1 ? "s" : "") : "");

        return new StringBuilder().append(fDays).append(fHours).append(fMinutes).append(fSeconds).toString();
    }

    public static String getNum(String input) {
        return input.replaceAll("[^0-9]", "");
    }


    public static String stripColor(String input) {
        return ChatColor.stripColor(input);
    }
/*
    public static void increaseEnchant(ItemStack item, String identifier) {
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return;
        }
        List<String> lore = meta.getLore();
        increaseEnchant(identifier, lore);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void increaseEnchant(ItemStack item, String identifier, int amt) {
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return;
        }
        List<String> lore = meta.getLore();
        //Added and changed contains
        for (int index = 0; index < lore.size(); index++) {
            String line = lore.get(index);
            if (stripColor(line).contains(stripColor(Txt.parse(identifier)))) {
                lore.set(index, Txt.parse(identifier + " " + amt));
            }
        }
        //
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void setEnchant(String identifier, List<String> lore, int amt) {
        for (int index = 0; index < lore.size(); index++) {
            String line = lore.get(index);
            if (stripColor(line).startsWith(stripColor(Txt.parse(identifier)))) {
                lore.set(index, Txt.parse(identifier + " " + amt));
            }
        }
    }

    public static void increaseEnchant(String identifier, List<String> lore) {
        for (int index = 0; index < lore.size(); index++) {
            String line = lore.get(index);
            if (stripColor(line).startsWith(stripColor(Txt.parse(identifier)))) {
                int value = Integer.parseInt(getNum(stripColor(line)));
                lore.set(index, Txt.parse(identifier + " " + --value));

            }
        }
    }

    public static boolean hasStringInLore(String s, List<String> lore) {
        for (String line : lore) {
            if (stripColor(line).contains(stripColor(Txt.parse(s)))) {
                return true;
            }
        }
        return false;
    }*/

    public static boolean areTheSameItems(ItemStack compare, ItemStack compareTo) {
        if(compare == null || compareTo == null)
            return false;
        if(compare.getType() != compareTo.getType())
            return false;
        if(!compare.hasItemMeta() || !compareTo.hasItemMeta())
            return false;
        if(!compare.getItemMeta().hasLore() || !compareTo.getItemMeta().hasLore())
            return false;
        if(compare.getItemMeta().getLore().size() != compareTo.getItemMeta().getLore().size())
            return false;
        if(!stripColor(compare.getItemMeta().getDisplayName()).equalsIgnoreCase(stripColor(compareTo.getItemMeta().getDisplayName())))
            return false;
        int holder = 0;
        for(String n : compare.getItemMeta().getLore()) {
            if(!stripColor(n).equalsIgnoreCase(stripColor(compareTo.getItemMeta().getLore().get(holder++))))
                return false;
        }
        return true;
    }

    public static boolean areTheSameItemsNoLore(ItemStack compare, ItemStack compareTo) {
        if(compare == null || compareTo == null)
            return false;
        if(compare.getType() != compareTo.getType())
            return false;
        if(!compare.hasItemMeta() || !compareTo.hasItemMeta())
            return false;
        if(!compare.getItemMeta().hasLore() || !compareTo.getItemMeta().hasLore())
            return false;
        if(compare.getItemMeta().getLore().size() != compareTo.getItemMeta().getLore().size())
            return false;
        if(!stripColor(compare.getItemMeta().getDisplayName()).equalsIgnoreCase(stripColor(compareTo.getItemMeta().getDisplayName())))
            return false;
        return true;
    }

    /*
    public static int getInt(String identifier, List<String> lore) {
        if(identifier.equalsIgnoreCase(""))
            return 0;
        for (String line : lore) {
            if (stripColor(line).contains(stripColor(Txt.parse(identifier)))) {
                int ret = 0;
                try {
                    ret = Integer.parseInt(getNum(stripColor(line)));
                } catch (NumberFormatException e) {
                }
                return ret;
            }
        }
        return 0;
    }*/

    public static void removeOneItemInHandFromPlayer(Player player) {
        if(player.getInventory().getItemInHand().getAmount() == 1) {
            player.getInventory().setItemInHand(null);
        } else {
            player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
        }
    }

    public static boolean areSameLocations(Location loc1, Location loc2) {
        if(loc1.getWorld().getName().equalsIgnoreCase(loc2.getWorld().getName())
                && loc1.getBlockX() == loc2.getBlockX()
                && loc1.getBlockY() == loc2.getBlockY()
                && loc1.getBlockZ() == loc2.getBlockZ())
            return true;
        return false;
    }

}
