package me.koro.ksjudge.Utility;

import me.koro.ksjudge.KSJudge;
import me.koro.ksjudge.SQL.SQLCreate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static KSJudge plugin = KSJudge.getInstance();
    private final LPUtils lpUtils = new LPUtils(plugin.luckPerms);
    private final SQLUtils sqlUtils = new SQLUtils();

    public Utils() {

    }

    // Nice little method to create a gui item with a custom name, and description
    public static ItemStack createGuiItem(Material material, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        String l = ChatColor.BLUE + String.join(" ", lore);

        List<String> metalore = Arrays.asList(ChatPaginator.wordWrap(l, 50));

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(metalore);

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getHead(OfflinePlayer player, String... lore){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(player.getName());

        Player p = (Player)player;
        String plotStatus = sqlUtils.getPlotStatus(sqlUtils.getPlotID(p));
        String playerGroup = lpUtils.getPlayerGroup(player);

        List<String> loreList = new ArrayList<>();
        loreList.add(PlotUtils.printId(p, PlotUtils.getId(p)));
        loreList.add(ChatColor.GRAY + "Status: " + ChatColor.GOLD + plotStatus);
        loreList.add(ChatColor.GRAY + "Rank:" + ChatColor.GOLD + playerGroup);

        meta.setLore(loreList);
        skull.setItemMeta(meta);

        return skull;
    }

    public ItemStack getHead(OfflinePlayer player, String id){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(player.getName());

        List<String> l = new ArrayList<>();
        l.add(id);
        if(PlotUtils.getId((Player) player) != null) {
            l.add(ChatColor.GRAY + "Status: " + ChatColor.GOLD + sqlUtils.getPlotStatus(id));
            l.add(ChatColor.GRAY + "Rank: " + ChatColor.GOLD + lpUtils.getPlayerGroup(player));
        }
        meta.setLore(l);
        skull.setItemMeta(meta);

        return skull;
    }

}

