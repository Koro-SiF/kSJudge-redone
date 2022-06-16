package me.koro.ksjudge.Commands;

import me.koro.ksjudge.KSJudge;
import me.koro.ksjudge.Menus.PlotOverviewMenu;
import me.koro.ksjudge.Utility.PlotUtils;
import me.koro.ksjudge.Utility.SQLUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Submit implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private KSJudge plugin;

    public Submit(KSJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("submit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (!(sender instanceof Player)) { this.plugin.getConfig().getString("Console.error"); return true;}

        Player player = (Player) sender;
        if(!player.hasPermission("kjudge.submit")) {
            player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "kjudge.submit");
            return true;
        }

        sqlUtils.setPlotTable(player);
        if (PlotUtils.getId(player) != null) {
            sqlUtils.addPlotID(PlotUtils.getId(player).toString());
        } else
            player.sendMessage(ChatColor.RED + "You must stand on your plot");

        new PlotOverviewMenu(plugin.getPlayerMenuUtils(player)).open();
        return true;
    }

}
