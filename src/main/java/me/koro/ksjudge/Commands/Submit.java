package me.koro.ksjudge.Commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotId;
import me.koro.ksjudge.KSJudge;
import me.koro.ksjudge.Menus.PlotOverviewMenu;
import me.koro.ksjudge.Utility.PlotUtils;
import me.koro.ksjudge.Utility.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
        if(!player.hasPermission("ksjudge.submit")) {
            player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "ksjudge.submit");
            return true;
        }

        PlotId id = PlotUtils.getId(player);

        if(id == null) {
            player.sendMessage(ChatColor.RED + "You must stand on your plot");
            return true;
        }

        PlotPlayer p = BukkitUtil.adapt(player);

        boolean isOwner = Bukkit.getOfflinePlayer(p.getCurrentPlot().getOwner()).getName() == player.getName();

        if (!isOwner) {
            player.sendMessage(ChatColor.RED + "You must stand on your plot");
            return true;
        }

        sqlUtils.setPlotTable(player);

        sqlUtils.addPlotID(id.toString());
        new PlotOverviewMenu(KSJudge.getPlayerMenuUtils(player)).open();
        return true;
    }

}
