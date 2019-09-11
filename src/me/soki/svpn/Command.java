package me.soki.svpn;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Command implements CommandExecutor {

    char doubleArrow = '\u0187';

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!sender.isOp() || !sender.hasPermission("svpn.admin")){
            sender.sendMessage(ConfigService.getNoPermission());
            return false;
        }
        if (args.length == 0){
            sendHelp(sender, label);
            return false;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("online")){
            if (sVPN.getOnVPN().isEmpty()){
                sender.sendMessage(ChatColor.RED + "No players are currently using a VPN");
                return true;
            }
            sender.sendMessage(ChatColor.BLUE + "Listing all players on a VPN: ");
            for (UUID vpnUserUUID : sVPN.getOnVPN()) {
                Player vpnUser = Bukkit.getPlayer(vpnUserUUID);
                sender.sendMessage("  - " + vpnUser.getDisplayName());
            }
            return true;
        }
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("isvpn")){
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null){
                    if (Checker.isVPN(target).keySet().iterator().next()){
                        sender.sendMessage(ChatColor.GREEN + "Player " + target.getDisplayName() + ChatColor.GREEN + " is using a VPN. API: " + String.join(", ", Checker.isVPN(target).values().iterator().next())
                        + ". IP: " + (ConfigService.isDisplayIPs() ? target.getAddress().getHostName() : ChatColor.GRAY + ChatColor.ITALIC.toString() + "HIDDEN"));
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.RED + "Player " + target.getDisplayName() + ChatColor.RED + " is not using a VPN.");
                        return true;
                    }
                }else{
                    sender.sendMessage(ChatColor.RED + "Invalid Player.");
                    return false;
                }
            }
            if (args[0].equalsIgnoreCase("setkick")) {
                if (Boolean.valueOf(args[1]) != null) {
                    if (Boolean.valueOf(args[1])) {
                        sender.sendMessage(ChatColor.GREEN + "Enabled " + ChatColor.GRAY + "kicking.");
                        ConfigService.setKickEnabled(true);
                        return true;
                    }else {
                        sender.sendMessage(ChatColor.RED + "Disabled " + ChatColor.GRAY + "kicking.");
                        ConfigService.setKickEnabled(false);
                        return true;
                    }
                }else{
                    sender.sendMessage(ChatColor.RED + "Cannot resolve boolean " + args[1] + ". You must use true or false");
                    return false;
                }
            }
        }
        sendHelp(sender, label);

        return false;
    }

    private void sendHelp(CommandSender sender, String label) {
        ChatColor descColor = ChatColor.WHITE;
        String prefix = ChatColor.GRAY + "  " + doubleArrow + " /" + label + " ";
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------");
        sender.sendMessage(ChatColor.BLUE + "   sVPN Help " + ChatColor.GRAY + "(Page 1/1)");
        sender.sendMessage(prefix + "isvpn <player> - " + descColor + "Checks if a player is using a VPN");
        sender.sendMessage(prefix + "setkick <true/false> - " + descColor + "Enables/disables kicking if a VPN is in use");
        sender.sendMessage(prefix + "online - " + descColor + "Lists all online players with VPNs enabled");
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------");
    }
}
