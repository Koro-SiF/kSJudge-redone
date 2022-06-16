package me.koro.ksjudge.Utility;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotId;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlotUtils {

    public PlotUtils(){
    }

    public static PlotId getId(OfflinePlayer player){
        PlotPlayer p = BukkitUtil.adapt((Player) player);
        Plot plot = p.getCurrentPlot();

        if(plot == null) return null;

        if(plot.hasOwner()) return plot.getId();

        return null;
    }

    public static String printId(Player player, PlotId id){
        PlotPlayer p = BukkitUtil.adapt(player);
        Plot plot = p.getCurrentPlot();

        if(id == null) return "You must be at your plot";

        if(plot.isOwner(p.getUUID())){
            return "Plot ID: " + id.toString();
        } else {
            return "You must be at your plot";
        }
    }

}
