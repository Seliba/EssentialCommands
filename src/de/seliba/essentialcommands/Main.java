package de.seliba.essentialcommands;

/*
EssentialCommands created by Seliba
*/

import de.seliba.essentialcommands.commands.*;
import de.seliba.essentialcommands.config.Config;
import de.seliba.essentialcommands.tpa.TpaManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config cfg, msg;
    private TpaManager tpaManager;

    @Override
    public void onEnable() {
        System.out.println("[EssentialCommands] Gestartet!");

        getCommand("time").setExecutor(new TimeCommand(this));
        getCommand("toggledownfall").setExecutor(new ToggledownfallCommand(this));
        getCommand("gamemode").setExecutor(new GamemodeCommand(this));
        getCommand("gm").setExecutor(new GamemodeCommand(this));
        getCommand("nightvision").setExecutor(new NightvisionCommand(this));
        getCommand("speedbuild").setExecutor(new SpeedbuildCommand(this));
        getCommand("walkspeed").setExecutor(new WalkspeedCommand(this));
        getCommand("flyspeed").setExecutor(new FlyspeedCommand(this));
        getCommand("tpa").setExecutor(new TpaCommand(this));

        System.out.println("[EssentialCommands] Commands registriert!");

        config();

        System.out.println("[EssentialCommands] Config geladen!");

        tpaManager = new TpaManager(this);
    }

    @Override
    public void onDisable() {
        System.out.println("[EssentialCommands] Gestoppt!");
    }

    private void config() {
        cfg = new Config("config.yml", this);
        msg = new Config("messages.yml", this);
        if(cfg.getString("prefix") == null) {
            cfg.set("prefix", "&7[&6EssentialCommands&7]");
            cfg.set("tpa.max-time-in-seconds", 180L);
            cfg.set("tpa.time-until-teleport-in-seconds", 3L);
            cfg.save();
        }
        if(msg.getString("no-permissions") == null) {
            msg.set("no-permissions", "&cDazu hast du keine Rechte!");
            msg.set("no-player", "&cDazu musst du ein Spieler sein!");
            msg.set("commands.time.wrongusage", "&cBitte benutze &6/time <set / add> <Zeit / Day / Night / Midnight / Noon>&c!");
            msg.set("commands.time.invalid-time", "&cDiese Uhrzeit existiert nicht!");
            msg.set("commands.time.successfully", "&aDie neue Zeit in &6%WORLD% &aist nun &6%TIME%&a!");
            msg.set("commands.toggledownfall.successfully", "&aDas Wetter wurde &6gecleart&a!");
            msg.set("commands.gm.wrongusage", "&cBitte benutze &6/gm <Gamemode> [Spieler]&c!");
            msg.set("commands.gm.wrong-gamemode", "&cDieser Gamemode existiert nicht!");
            msg.set("commands.gm.player-not-found", "&cDieser Spieler ist nicht online!");
            msg.set("commands.gm.successfully.self", "&aDu bist nun im Gamemode &6%GAMEMODE%&a!");
            msg.set("commands.gm.successfully.other.self", "&aDer Spieler &6%PLAYER% &aist nun im Gamemode &6%GAMEMODE%&a!");
            msg.set("commands.gm.successfully.other.other", "&aDer Spieler &6%PLAYER% &ahat dich in den Gamemode &6%GAMEMODE% &agesetzt!");
            msg.set("commands.nightvision.successfully.removed", "&aDu hast nun keine Nachtsicht mehr!");
            msg.set("commands.nightvision.successfully.added", "&aDu hast nun Nachtsicht!");
            msg.set("commands.speedbuild.successfully.removed", "&aDu hast nun kein Speedbuild mehr!");
            msg.set("commands.speedbuild.successfully.added", "&aDu hast nun Speedbuild!");
            msg.set("commands.walkspeed.wrongusage", "&cBitte benutze &6/walkspeed <1 - 10, Reset>");
            msg.set("commands.walkspeed.invalid-speed", "&cDiese Geschwindigkeit ist nicht möglich!");
            msg.set("commands.walkspeed.successfully", "&aDu hast deinen Walkspeed zu &6%SPEED% geändert!");
            msg.set("commands.flyspeed.wrongusage", "&cBitte benutze &6/flyspeed <1 - 10, Reset>");
            msg.set("commands.flyspeed.invalid-speed", "&cDiese Geschwindigkeit ist nicht möglich!");
            msg.set("commands.flyspeed.successfully", "&aDu hast deinen Flyspeed zu &6%SPEED% &ageändert!");
            msg.set("commands.tpa.wrongusage", "&cBitte benutze &6/tpa <Spieler>&c!");
            msg.set("commands.tpa.successfully.self", "&aDu hast die Tpa von &6%PLAYER% &aangenommen! Teleportiere in 3 Sekunden...");
            msg.set("commands.tpa.successfully.other", "&6%PLAYER% &ahat deine Tpa angenommen! Teleportiere in 3 Sekunden...");
            msg.set("commands.tpa.successfully.teleported", "&aTeleportiere...");
            msg.set("commands.tpa.new-request.self", "&aDu hast eine Tpa an &6%PLAYER% &agesendet!");
            msg.set("commands.tpa.new-request.other", "&aDu hast eine Tpa von &6%PLAYER% &aerhalten!");
            msg.set("commands.tpa.declined.self", "&aDu hast die Tpa von &6%PLAYER% &aabgelehnt!");
            msg.set("commands.tpa.declined.other", "&6%PLAYER% &ahat deine Tpa abgelehnt!");
            msg.set("commands.tpa.invalid-request", "&cDu hast keine gültige Tpa-Anfrage!");
            msg.save();
        }
    }

    public Config getCfg() {
        return cfg;
    }

    public TpaManager getTpaManager() {
        return tpaManager;
    }

    public String getConfigMsg(String path) {
        return getPrefix() + ChatColor.translateAlternateColorCodes('&', msg.getString(path));
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', cfg.getString("prefix")) + " ";
    }

}
