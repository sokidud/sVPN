package me.soki.svpn;

import me.scalebound.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class sVPN extends JavaPlugin {

    private static List<UUID> onVPN = new ArrayList<>();
    private static Configuration config;


    @Override
    public void onEnable() {
        config = this.getConfig();
        ConfigService.setupConfig(this);
        this.saveDefaultConfig();
        getCommand("svpn").setExecutor(new Command());
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        Checker.setup();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (ConfigService.isKickEnabled() != config.getBoolean("settings.kick.kick-enabled")){
            ConfigService.setupConfig(this);
            this.saveDefaultConfig();
            config.set("settings.kick.kick-enabled", !ConfigService.isKickEnabled());

            this.saveConfig();
        }
    }

    public static void sendStaffMessage(String message, String permission, Player player, Boolean sendToSender){
        for (Player players : Bukkit.getServer().getOnlinePlayers()){
            if (players.hasPermission(permission)){
                if (player != null) {
                    if (players.equals(player) && !sendToSender) {
                        continue;
                    }
                }
                players.sendMessage(message);
            }
        }
    }


    public static List<UUID> getOnVPN() {
        return onVPN;
    }

    public void setOnVPN(List<UUID> onVPN) {
        this.onVPN = onVPN;
    }

}
