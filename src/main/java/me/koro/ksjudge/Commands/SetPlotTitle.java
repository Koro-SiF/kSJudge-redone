package me.koro.ksjudge.Commands;

import me.koro.ksjudge.KSJudge;
import me.koro.ksjudge.Utility.PlotUtils;
import me.koro.ksjudge.Utility.SQLUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetPlotTitle implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private KSJudge plugin;

    public SetPlotTitle(KSJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("ptitle").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(!(sender instanceof Player)){ this.plugin.getConfig().getString("Console.error"); return true;}

        Player p = (Player) sender;

        if(!(p.hasPermission("ksjudge.ptitle"))) {
            p.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "ksjudge.ptitle");
            return true;
        }

        if(PlotUtils.getId(p) == null) {
            p.sendMessage(ChatColor.RED + "You must be on your plot");
            return true;
        }

        sqlUtils.setPlotTable(p);
        if (args.length == 0) p.sendMessage(ChatColor.GREEN + "Input a title for you plot: /ptitle [title]");

        List<String> title = new ArrayList<>();
        for (String s : args) {
            title.add(s);
            sqlUtils.addPlotTitle(PlotUtils.getId(p).toString(), String.join(" ", title));
        }

        p.sendMessage(ChatColor.GOLD + "Title updated");

        return true;
    }
}
