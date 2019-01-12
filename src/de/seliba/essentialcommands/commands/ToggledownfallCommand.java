package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggledownfallCommand implements CommandExecutor {

    private Main main;

    public ToggledownfallCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("system.toggledownfall")) {
                player.getWorld().setStorm(false);
                player.getWorld().setThundering(false);
                player.sendMessage(main.getConfigMsg("commands.toggledownfall.successfully"));
            } else {
                player.sendMessage(main.getConfigMsg("no-permissions"));
            }
        } else {
            sender.sendMessage(main.getConfigMsg("no-player"));
        }
        return true;
    }
}
