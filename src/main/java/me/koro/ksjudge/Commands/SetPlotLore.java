package me.koro.ksjudge.Commands;

import me.koro.ksjudge.KSJudge;
import me.koro.ksjudge.Utility.PlotUtils;
import me.koro.ksjudge.Utility.SQLUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetPlotLore implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private KSJudge plugin;

    public SetPlotLore(KSJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("plore").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){ this.plugin.getConfig().getString("Console.error"); return true;}

        Player p = (Player) sender;
        if(!(p.hasPermission("kjudge.plore"))) {
            p.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "kjudge.plore");
            return true;
        }

        if(PlotUtils.getId(p) == null) {
            p.sendMessage(ChatColor.RED + "You must be on your plot");
            return true;
        }

        sqlUtils.setPlotTable(p);

        if (args.length == 0) {
            TextComponent loreEdit = new TextComponent(ChatColor.GREEN + "[Edit plot lore] ");
            TextComponent loreAdd = new TextComponent(ChatColor.BLUE + "[Add to lore]");

            loreEdit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore edit "));
            loreEdit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                   new ComponentBuilder(ChatColor.GRAY + "Click to edit lore").create()));

            loreAdd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore add "));
            loreAdd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                  new ComponentBuilder(ChatColor.GRAY + "Click to add to lore").create()));

            p.spigot().sendMessage(loreEdit, loreAdd);
            return true;
        }

        switch (args[0].toLowerCase()){
            case "edit" -> {
                List<String> lore = new ArrayList<>();
                for (String s : args) {
                    lore.add(s);
                }
                lore.remove(0);
                sqlUtils.addPlotLore(PlotUtils.getId(p).toString(), String.join(" ", lore));
                p.sendMessage(ChatColor.GOLD + "Lore updated");
                break;
            }
            case "add" -> {
                List<String> loreadd = new ArrayList<>();
                List<String> orlore = new ArrayList<>();
                orlore.add(sqlUtils.getPlotLore(PlotUtils.getId(p).toString()));
                for (String s : args) {
                    loreadd.add(s);
                }
                loreadd.remove(0);
                List<String> addedlore = Stream.of(orlore, loreadd)
                        .flatMap(x -> x.stream())
                        .collect(Collectors.toList());
                sqlUtils.addPlotLore(PlotUtils.getId(p).toString(), String.join(" ", addedlore));
                p.sendMessage(ChatColor.GOLD + "Lore updated");
                break;
            }
        }

        return true;
    }
}
