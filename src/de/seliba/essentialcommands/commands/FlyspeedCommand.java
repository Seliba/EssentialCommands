package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyspeedCommand implements CommandExecutor {

    private Main main;

    public FlyspeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("system.flyspeed")) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("reset")) {
                        player.setFlySpeed(0.1f);
                        player.sendMessage(main.getConfigMsg("commands.flyspeed.successfully").replaceAll("%SPEED%", String.valueOf(player.getFlySpeed() * 10)));
                    } else if(isValue(args[0])) {
                        float speed = Float.valueOf(args[0]);
                        if(speed >= 0 && speed <= 10) {
                            player.setFlySpeed(speed / 10);
                            player.sendMessage(main.getConfigMsg("commands.flyspeed.successfully").replaceAll("%SPEED%", String.valueOf(player.getFlySpeed() * 10)));
                        } else {
                            player.sendMessage(main.getConfigMsg("commands.flyspeed.invalid-speed"));
                        }
                    } else {
                        player.sendMessage(main.getConfigMsg("commands.flyspeed.wrongusage"));
                    }
                } else {
                    player.sendMessage(main.getConfigMsg("commands.flyspeed.wrongusage"));
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
