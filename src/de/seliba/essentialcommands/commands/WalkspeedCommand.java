package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WalkspeedCommand implements CommandExecutor {

    private Main main;

    public WalkspeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("system.walkspeed")) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("reset")) {
                        player.setWalkSpeed(0.2f);
                        player.sendMessage(main.getConfigMsg("commands.walkspeed.successfully").replaceAll("%SPEED%", String.valueOf(player.getWalkSpeed() * 10)));
                    } else if(isValue(args[0])) {
                        float speed = Float.valueOf(args[0]);
                        if(speed >= 0 && speed <= 10) {
                            player.setWalkSpeed(speed / 10);
                            player.sendMessage(main.getConfigMsg("commands.walkspeed.successfully").replaceAll("%SPEED%", String.valueOf(player.getWalkSpeed() * 10)));
                        } else {
                            player.sendMessage(main.getConfigMsg("commands.walkspeed.invalid-speed"));
                        }
                    } else {
                        player.sendMessage(main.getConfigMsg("commands.walkspeed.wrongusage"));
                    }
                } else {
                    player.sendMessage(main.getConfigMsg("commands.walkspeed.wrongusage"));
                }
            } else {
                player.sendMessage(main.getConfigMsg("no-permissions"));
            }
        } else {
            sender.sendMessage(main.getConfigMsg("no-player"));
        }
        return true;
    }

    private boolean isValue(String string) {
        try {
            Float.valueOf(string);
            return true;
        } catch(NumberFormatException ex) {
            return false;
        }
    }

}
