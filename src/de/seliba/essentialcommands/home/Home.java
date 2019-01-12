package de.seliba.essentialcommands.home;

import de.seliba.essentialcommands.Main;
import de.seliba.essentialcommands.home.util.Config;
import de.seliba.essentialcommands.home.util.MySQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Home implements Listener, CommandExecutor {

    private Config config;
    private MySQLHandler handler;

    private String sethome, homeError, homeSuccess;

    private Map<UUID, Location> homes;

    private Main main;

    public Home(Main main) {
        config = new Config("homes.yml", main);
        config.setDefault("host", "localhost");
        config.setDefault("port", 3306);
        config.setDefault("username", "root");
        config.setDefault("password", "");
        config.setDefault("database", "homes");
        config.setDefault("server_name", "lobby");

        config.setDefault("sethome", "Dein Zuhause wurde gesetzt!");
        config.setDefault("home_error", "Du hast kein Zuhause!");
        config.setDefault("home_success", "Du bist jetzt zuhause");
        config.save();
        handler = new MySQLHandler(
                config.getString("host"),
                config.getInt("port"),
                config.getString("username"),
                config.getString("password"),
                config.getString("database"),
                config.getString("server_name")
        );
        sethome = config.getTranslatedString("sethome");
        homeError = config.getTranslatedString("home_error");
        homeSuccess = config.getTranslatedString("home_success");
        homes = new HashMap<>();

        handler.connect();
        handler.createTable("homes", "`uuid` VARCHAR(36) NOT NULL PRIMARY KEY," +
                "`server` VARCHAR(64)," +
                "`world` VARCHAR(64)," +
                "`x` DOUBLE," +
                "`y` DOUBLE," +
                "`z` DOUBLE," +
                "`pitch` FLOAT," +
                "`yaw` FLOAT");

        Bukkit.getOnlinePlayers().forEach(this::loadHome);

        Bukkit.getPluginManager().registerEvents(this, main);

        main.getCommand("home").setExecutor(this);
        main.getCommand("sethome").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;

            if (command.getName().equalsIgnoreCase("sethome")) {
                handler.setHome(player.getUniqueId(), player.getLocation());
                homes.put(player.getUniqueId(), player.getLocation());
                player.sendMessage(sethome);
                return true;
            } else if (command.getName().equalsIgnoreCase("home")) {
                if (!homes.containsKey(player.getUniqueId())) {
                    player.sendMessage(homeError);
                    return false;
                }
                player.teleport(homes.get(player.getUniqueId()));
                player.sendMessage(homeSuccess);
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        loadHome(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        homes.remove(event.getPlayer().getUniqueId());
    }

    private void loadHome(Player player) {
        UUID uuid = player.getUniqueId();
        Location location = handler.getHome(uuid);

        if (location != null)
            homes.put(uuid, location);
    }
}
