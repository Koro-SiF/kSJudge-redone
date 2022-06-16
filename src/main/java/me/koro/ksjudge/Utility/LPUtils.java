package me.koro.ksjudge.Utility;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LPUtils {

    private final LuckPerms lp;

    public LPUtils(LuckPerms lp) {
        this.lp = lp;
    }

    public String getPlayerGroup(OfflinePlayer player){

        List<String> finalGroup = new ArrayList<>();
        List<String> allGroups = getAllGroups(player);

        for(int i = 0; i < allGroups.size(); i++){
            for(int j = 0; j < creativeGroups().size(); j++){
                if(allGroups.get(i).contains(creativeGroups().get(j))){
                    finalGroup.add(allGroups.get(i));
                }
            }
        }

        return String.join(", ", finalGroup);
    }

    public List<String> getAllGroups(OfflinePlayer player){

        // Get a Bukkit player adapter.
        PlayerAdapter<Player> playerAdapter = lp.getPlayerAdapter(Player.class);

        // Get a LuckPerms user for the player.
        User user = playerAdapter.getUser((Player) player);

        // Get all of the groups they inherit from on the current server.
        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions((Player) player));

        // Convert to a comma separated string (e.g. "admin, mod, default")
        String groupsString = groups.stream().map(Group::getName).collect(Collectors.joining(", "));

        List<String> allGroups = Arrays.asList(groupsString.split(", "));

        return allGroups;
    }

    public List<String> creativeGroups(){
        List<String> creativeGroupsList = Arrays.asList("novice", "disciple", "mentor", "guru", "expert", "master");
        return creativeGroupsList;
    }
}
