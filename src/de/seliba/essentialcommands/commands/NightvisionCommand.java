package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightvisionCommand implements CommandExecutor {

    private Main main;

    public NightvisionCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("system.nightvision")) {
                if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    player.sendMessage(main.getConfigMsg("commands.nightvision.successfully.removed"));
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999999, 10));
                    player.sendMessage(main.getConfigMsg("commands.nightvision.successfully.added"));
                }
            } else {
                player.sendMessage(main.getConfigMsg("no-permissions"));
            }
        } else {
            sender.sendMessage(main.getConfigMsg("no-player"));
        }
        return true;
    }

}
