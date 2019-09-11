package me.soki.svpn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.security.auth.login.Configuration;

public class ConfigService {

    private static String noPermission;
    private static boolean kickEnabled;
    private static String kickMessage;
    private static String kickStaffMessage;
    private static String kickBypassSelfMessage;
    private static String kickBypassStaffMessage;
    private static boolean notify;
    private static boolean displayIPs;
    private static FileConfiguration configuration;
    public static void setupConfig(Plugin plugin){
        configuration = plugin.getConfig();

        if (configuration.get("no-permission-msg") != null){
            noPermission = color(configuration.getString("no-permission-msg"));
        }
        if (configuration.get("settings.kick.kick-enabled") != null){
            kickEnabled = configuration.getBoolean("settings.kick.kick-enabled");
        }
        if (configuration.get("settings.kick.kick-message") != null){
            kickMessage = color(configuration.getString("settings.kick.kick-message"));
        }
        if (configuration.get("settings.kick.kick-staff-message") != null){
            kickStaffMessage = color(configuration.getString("settings.kick.kick-staff-message"));
        }
        if (configuration.get("settings.kick.notify-staff") != null){
            notify = configuration.getBoolean("settings.kick.notify-staff");
        }
        if (configuration.get("settings.kick.kick-bypass-staff-message") != null){
            kickBypassStaffMessage = color(configuration.getString("settings.kick.kick-bypass-staff-message"));
        }
        if (configuration.get("settings.kick.kick-bypass-self-message") != null){
            kickBypassSelfMessage = color(configuration.getString("settings.kick.kick-bypass-self-message"));
        }
        if (configuration.get("settings.display-ips") != null){
            displayIPs = configuration.getBoolean("settings.display-ips");
        }
    }

    private static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String getNoPermission() {
        return noPermission;
    }

    public static boolean isKickEnabled() {
        return kickEnabled;
    }

    public static String getKickMessage() {
        return kickMessage;
    }

    public static boolean isNotify() {
        return notify;
    }

    public static String getKickStaffMessage() {
        return kickStaffMessage;
    }

    public static String getKickBypassSelfMessage() {
        return kickBypassSelfMessage;
    }

    public static String getKickBypassStaffMessage() {
        return kickBypassStaffMessage;
    }

    public static boolean isDisplayIPs() {
        return displayIPs;
    }

    public static FileConfiguration getConfiguration() {
        return configuration;
    }

    public static void setKickEnabled(boolean kickEnabled) {
        ConfigService.kickEnabled = kickEnabled;
    }
}
