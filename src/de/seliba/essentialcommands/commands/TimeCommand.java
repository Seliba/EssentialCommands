package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    private Main main;

    public TimeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("system.time")) {
                if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("add")) {
                        if(isLong(args[1])) {
                            player.getWorld().setTime(player.getWorld().getTime() + Long.valueOf(args[1]));
                        } else {
                            player.sendMessage(main.getConfigMsg("commands.time.invalid-time"));
                            return true;
                        }
                        player.sendMessage(main.getConfigMsg("commands.time.successfully").replaceAll("%TIME%", String.valueOf(player.getWorld().getTime()).toUpperCase()).replaceAll("%WORLD%", player.getWorld().getName().toUpperCase()));
                    } else if(args[0].equalsIgnoreCase("set")) {
                        if(args[1].equalsIgnoreCase("day")) {
                            player.getWorld().setTime(1000L);
                        } else if(args[1].equalsIgnoreCase("night")) {
                            player.getWorld().setTime(13000L);
                        } else if(args[1].equalsIgnoreCase("noon")) {
                            player.getWorld().setTime(6000L);
                        } else if(args[1].equalsIgnoreCase("midnight")) {
                            player.getWorld().setTime(18000L);
                        } else if(isLong(args[1])) {
                            long time = Long.valueOf(args[1]);
                            if(time >= 0L && time <= 24000L) {
                                player.getWorld().setTime(time);
                            } else {
                                player.sendMessage(main.getConfigMsg("commands.time.invalid-time"));
                                return true;
                            }
                        } else {
                            player.sendMessage(main.getConfigMsg("commands.time.invalid-time"));
                            return true;
                        }
                        player.sendMessage(main.getConfigMsg("commands.time.successfully").replaceAll("%TIME%", args[1].toUpperCase()).replaceAll("%WORLD%", player.getWorld().getName().toUpperCase()));
                    } else {
                        player.sendMessage(main.getConfigMsg("commands.time.wrongusage"));
                        return true;
                    }
                } else {
                    player.sendMessage(main.getConfigMsg("commands.time.wrongusage"));
                    return true;
                }
            } else {
                player.sendMessage(main.getConfigMsg("no-permissions"));
                return true;
            }
        } else {
            sender.sendMessage(main.getConfigMsg("no-player"));
            return true;
        }
        return true;
    }

    private boolean isLong(String string) {
        try {
            Long.valueOf(string);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
