package me.soki.svpn;

import com.google.common.collect.Maps;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParser;
import net.minecraft.util.org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.*;

public class Checker {

    private static HashMap<String, String> services = new HashMap<>();
    //returns if on VPN and which service was used to check it

    public static void setup() {
        services.put("IPQS", "https://www.ipqualityscore.com/api/json/ip/rNRVbNkgESiBKn6Yvw6RQWi0CI12e515/%ip%");
    }

    public static Map<Boolean, List<String>> isVPN(Player p) {
        return isVPN(p.getAddress().getHostName());
    }

    private static Map<Boolean, List<String>> isVPN(String hostname){
        List<String> detectors = new ArrayList<>();

        //if (hostname.equalsIgnoreCase("localhost") || hostname.equalsIgnoreCase("127.0.0.1")) return Collections.singletonMap(false, null);

        JsonParser parser = new JsonParser();
        for (String service : services.keySet()) {
            try {
                JsonElement jsonTree = parser.parse(get(services.get(service).replace("%ip%", hostname)));

                if (jsonTree.isJsonObject()) {
                    JsonObject jsonObject = jsonTree.getAsJsonObject();
                    if (jsonObject.get("vpn").getAsBoolean() || jsonObject.get("proxy").getAsBoolean()) {
                        detectors.add(service);
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception ex) {
                System.out.println("Error contacting API: " + service);
                ex.printStackTrace();
                return null;
            }
        }
        return Collections.singletonMap(!detectors.isEmpty(), detectors.isEmpty() ? null : detectors);
    }


    // HTTP GET request
    private static String get(String url) throws Exception {

        String USER_AGENT = "Mozilla/5.0";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        /* optional default is GET */
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return String.valueOf(response);
    }
}
