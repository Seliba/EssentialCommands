package de.seliba.essentialcommands.tpa;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TpaManager {

    private HashMap<Player, Player> tpaRequests;
    private Collection<Player> toTeleport;
    private Main main;

    public TpaManager(Main main) {
        this.main = main;
        tpaRequests = new HashMap<>();
        toTeleport = new ArrayList<>();
    }

    public void addTpaRequest(Player player, Player target) {
        tpaRequests.put(player, target);
    }

    public void removeTpaRequest(Player player) {
        tpaRequests.remove(player);
    }

    public boolean hasTpaRequest(Player player) {
        return tpaRequests.containsKey(player);
    }

    public Player getTpaPlayer(Player player) {
        return tpaRequests.get(player);
    }

    public void addToTeleport(Player player) {
        toTeleport.add(player);
    }

    public boolean containsToTeleport(Player player) {
        return toTeleport.contains(player);
    }

    public void removeFromTeleport(Player player) {
        toTeleport.remove(player);
    }

}
