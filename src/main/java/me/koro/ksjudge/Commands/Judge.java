package me.koro.ksjudge.Commands;

import me.koro.ksjudge.KSJudge;
import me.koro.ksjudge.Menus.JudgeMenus.JudgeMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Judge implements CommandExecutor {
    private KSJudge plugin;

    public Judge(KSJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("judge").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
        } else {
            Player player = (Player) sender;
            if(player.hasPermission("ksjudge.judge")) {
                new JudgeMenu(plugin.getPlayerMenuUtils(player)).open();
            } else player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "ksjudge.judge");
        }

        return true;
    }
}
