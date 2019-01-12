package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    private Main main;

    public GamemodeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("system.gm")) {
                    if(!setGamemode(player, args[0])) {
                        player.sendMessage(main.getConfigMsg("commands.gm.wrong-gamemode"));
                        return true;
                    }
                    player.sendMessage(main.getConfigMsg("commands.gm.successfully.self").replaceAll("%GAMEMODE%", player.getGameMode().name()));
                } else {
                    player.sendMessage(main.getConfigMsg("no-permissions"));
                }
            } else {
                sender.sendMessage(main.getConfigMsg("no-player"));
            }
        } else if(args.length == 2) {
            if(sender.hasPermission("system.gm")) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target != null) {
                    if(!setGamemode(target, args[0])) {
                        sender.sendMessage(main.getConfigMsg("commands.gm.wrong-gamemode"));
                        return true;
                    }
                    sender.sendMessage(main.getConfigMsg("commands.gm.successfully.other.self").replaceAll("%PLAYER%", target.getName().toUpperCase()).replaceAll("%GAMEMODE%", target.getGameMode().name()));
                    target.sendMessage(main.getConfigMsg("commands.gm.successfully.other.other").replaceAll("%PLAYER%", sender.getName().toUpperCase()).replaceAll("%GAMEMODE%", target.getGameMode().name()));
                } else {
                    sender.sendMessage(main.getConfigMsg("commands.gm.player-not-found"));
                    return true;
                }
            }
        } else {
            sender.sendMessage(main.getConfigMsg("commands.gm.wrongusage"));
        }
        return true;
    }

    private boolean setGamemode(Player player, String gamemode) {
        if(gamemode.equalsIgnoreCase("1") || gamemode.equalsIgnoreCase("creative") || gamemode.equalsIgnoreCase("c")) {
            player.setGameMode(GameMode.CREATIVE);
            return true;
        } else if(gamemode.equalsIgnoreCase("0") || gamemode.equalsIgnoreCase("survival") || gamemode.equalsIgnoreCase("su")) {
            player.setGameMode(GameMode.SURVIVAL);
            return true;
        } else if(gamemode.equalsIgnoreCase("2") || gamemode.equalsIgnoreCase("adventure") || gamemode.equalsIgnoreCase("a")) {
            player.setGameMode(GameMode.ADVENTURE);
            return true;
        } else if(gamemode.equalsIgnoreCase("3") || gamemode.equalsIgnoreCase("spectator") || gamemode.equalsIgnoreCase("sp")) {
            player.setGameMode(GameMode.SPECTATOR);
            return true;
        }
        return false;
    }

}
