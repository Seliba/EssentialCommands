package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedbuildCommand implements CommandExecutor {

    private Main main;

    public SpeedbuildCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("system.speedbuild"))
            if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                player.sendMessage(main.getConfigMsg("commands.speedbuild.successfully.removed"));
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000, 50));
                player.sendMessage(main.getConfigMsg("commands.speedbuild.successfully.added"));
            }
        }
        return true;
    }
}
