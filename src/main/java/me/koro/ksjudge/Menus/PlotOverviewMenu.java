package me.koro.ksjudge.Menus;

import me.koro.ksjudge.Utility.PlayerMenuUtils;
import me.koro.ksjudge.Utility.PlotUtils;
import me.koro.ksjudge.Utility.SQLUtils;
import me.koro.ksjudge.Utility.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlotOverviewMenu extends Menu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    Player p = playerMenuUtils.getOwner();

    public PlotOverviewMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.AQUA + "Plot Overview";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()){
            case WRITABLE_BOOK -> {
                new PlayerPlotsMenu(playerMenuUtils).open();
                break;
            }
            case PAPER -> {
                if(PlotUtils.getId(p) != null){ new PlotInfo(playerMenuUtils).open();}
                else p.sendMessage( ChatColor.RED + "This is not your plot");
                break;
            }
            case GREEN_CONCRETE -> {
                if(PlotUtils.getId(p) != null){
                    new SubmissionMenu(playerMenuUtils).open();
                }
                else p.sendMessage( ChatColor.RED + "This is not your plot");
                break;
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, utils.getHead(p));
        inventory.setItem(3, Utils.createGuiItem(Material.WRITABLE_BOOK, ChatColor.YELLOW + "List of your submitted plots", 1));
        if(PlotUtils.getId(p) != null){
            inventory.setItem(5, Utils.createGuiItem(Material.PAPER,
                    ChatColor.YELLOW + "Plot Submission Info", 1, "Title: " + sqlUtils.getPlotTitle(PlotUtils.getId(p).toString())));
        } else {
            inventory.setItem(5, Utils.createGuiItem(Material.PAPER,
                ChatColor.YELLOW + "Plot Submission Info", 1, "You must be on your plot"));
        }
        inventory.setItem(8, Utils.createGuiItem(Material.GREEN_CONCRETE, ChatColor.DARK_GREEN + "Submit Plot", 1, ""));
    }
}
