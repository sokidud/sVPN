package me.soki.svpn;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Events implements Listener {
    public static List<UUID> gettingKicked = new ArrayList<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (Checker.isVPN(p).keySet().iterator().next()){
            sVPN.getOnVPN().add(p.getUniqueId());
            if (p.hasPermission("svpn.bypass")){
                sVPN.sendStaffMessage(ConfigService.getKickBypassStaffMessage().replace("%API%", String.join(", ", Checker.isVPN(p).values().iterator().next())), "svpn.admin", p, false);
                p.sendMessage(ConfigService.getKickBypassSelfMessage().replace("%API%", String.join(", ", Checker.isVPN(p).values().iterator().next())));
                return;
            }
            if (!ConfigService.isKickEnabled()){
                return;
            }
            if (ConfigService.isNotify())
            sVPN.sendStaffMessage(ConfigService.getKickStaffMessage().replace("%API%", String.join(", ", Checker.isVPN(p).values().iterator().next()))
                    .replace("%IP%", ConfigService.isDisplayIPs() ? p.getAddress().getHostName() : ChatColor.GRAY + ChatColor.ITALIC.toString() + "HIDDEN")
                    .replace("%PLAYER%", p.getName()),
                    "svpn.admin", null, false);
            e.setJoinMessage("");
            gettingKicked.add(p.getUniqueId());
            p.kickPlayer(ConfigService.getKickMessage());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        sVPN.getOnVPN().remove(p.getUniqueId());
        if (gettingKicked.contains(p.getUniqueId())){
            e.setQuitMessage("");
            gettingKicked.remove(p.getUniqueId());
        }
    }
}
