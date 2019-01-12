package de.seliba.essentialcommands.config;

/*
EssentialCommands created by Seliba
*/

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config extends YamlConfiguration {

    private String name;
    private JavaPlugin javaPlugin;

    private File file;

    public Config(String name, JavaPlugin javaPlugin) {
        this.name = name;
        this.javaPlugin = javaPlugin;

        reload();
    }

    public void reload() {
        if (!javaPlugin.getDataFolder().exists())
            if (!javaPlugin.getDataFolder().mkdirs())
                throw new RuntimeException("Could not create ${NAME} " + name);

        file = new File(javaPlugin.getDataFolder(), name);

        try {
            if (!file.exists())
                if (!file.createNewFile())
                    throw new RuntimeException("Could not create ${NAME} " + name);

            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaults() {
        options().copyDefaults(true);
        save();
    }

    public String getTranslatedString(String path, String def) {
        return ChatColor.translateAlternateColorCodes('&', getString(path, def));
    }

    public String getTranslatedString(String path) {
        return getTranslatedString(path, "null");
    }

    public void setLocation(String path, Location location) {
        set(path + ".world", location.getWorld().getName());
        set(path + ".x", location.getX());
        set(path + ".y", location.getY());
        set(path + ".z", location.getZ());
        set(path + ".yaw", location.getYaw());
        set(path + ".pitch", location.getPitch());
    }

    public Location getLocation(String path) {
        return getLocation(path, null);
    }

    public Location getLocation(String path, Location def) {
        if (!isSet(path + ".world"))
            return def;

        return new Location(
                Bukkit.getWorld(getString(path + ".world")),
                getDouble(path + ".x"),
                getDouble(path + ".y"),
                getDouble(path + ".z"),
                (float) getDouble(path + ".yaw"),
                (float) getDouble(path + ".pitch")
        );
    }

    public boolean exists() {
        return file.exists();
    }

}
