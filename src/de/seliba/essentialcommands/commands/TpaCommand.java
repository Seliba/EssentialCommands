package de.seliba.essentialcommands.commands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {

    private Main main;

    public TpaCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("system.tpa")) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("accept")) {
                        if(main.getTpaManager().hasTpaRequest(player)) {
                            Player target = main.getTpaManager().getTpaPlayer(player);
                            if(target != null) {
                                main.getTpaManager().addToTeleport(target);
                                player.sendMessage(main.getConfigMsg("commands.tpa.successfully.self").replaceAll("%PLAYER%", target.getName().toUpperCase()));
                                target.sendMessage(main.getConfigMsg("commands.tpa.successfully.other").replaceAll("%PLAYER%", player.getName().toUpperCase()));
                            }
                            main.getServer().getScheduler().runTaskLaterAsynchronously(main, new Runnable() {
                                @Override
                                public void run() {
                                    if(main.getTpaManager().containsToTeleport(target) && target != null) {
                                        main.getTpaManager().removeFromTeleport(target);
                                        target.teleport(player);
                                        main.getTpaManager().removeTpaRequest(target);
                                        target.sendMessage(main.getConfigMsg("commands.tpa.successfully.teleported"));
                                    }
                                }
                            }, 20L * main.getConfig().getLong("tpa.time-until-teleport-in-seconds"));
                        } else {
                            player.sendMessage(main.getConfigMsg("commands.tpa.invalid-request"));
                        }
                    } else if(args[0].equalsIgnoreCase("decline")) {
                        if(main.getTpaManager().hasTpaRequest(player)) {
                            Player target = main.getTpaManager().getTpaPlayer(player);
                            player.sendMessage(main.getConfigMsg("commands.tpa.declined.self").replaceAll("%PLAYER%", target.getName().toUpperCase()));
                            target.sendMessage(main.getConfigMsg("commands.tpa.declined.other").replaceAll("%PLAYER%", player.getName().toUpperCase()));
                            main.getTpaManager().removeTpaRequest(target);
                        }
                    } else if(isPlayer(args[0])) {
                        Player target = Bukkit.getPlayer(args[0]);

                        TextComponent accept = new TextComponent("[✓]");
                        TextComponent decline = new TextComponent("[✖]");
                        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa accept"));
                        decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa decline"));
                        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Akzeptieren").color(ChatColor.GREEN).create()));
                        decline.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Ablehnen").color(ChatColor.DARK_RED).create()));
                        accept.setColor(ChatColor.GREEN);
                        decline.setColor(ChatColor.DARK_RED);
                        accept.setBold(true);

                        player.sendMessage(main.getConfigMsg("commands.tpa.new-request.self").replaceAll("%PLAYER%", target.getName().toUpperCase()));
                        target.sendMessage(main.getConfigMsg("commands.tpa.new-request.other").replaceAll("%PLAYER%", player.getName().toUpperCase()));
                        target.spigot().sendMessage(accept);
                        target.spigot().sendMessage(decline);

                        main.getTpaManager().addTpaRequest(target, player);
                    } else {
                        player.sendMessage(main.getConfigMsg("commands.tpa.wrongusage"));
                    }
                } else {
                    player.sendMessage(main.getConfigMsg("commands.tpa.wrongusage"));
                }
            } else {
                player.sendMessage(main.getConfigMsg("no-permissions"));
            }
        } else {
            sender.sendMessage(main.getConfigMsg("no-player"));
        }
        return true;
    }

    private boolean isPlayer(String string) {
        Player player = Bukkit.getPlayer(string);
        return player != null;
    }

}
