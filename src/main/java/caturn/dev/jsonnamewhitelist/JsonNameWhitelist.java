package caturn.dev.jsonnamewhitelist;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class JsonNameWhitelist extends JavaPlugin implements Listener {

    File jsonFile = new File("name-whitelist.json");
    // This file is located in the directory of your server

    @Override
    public void onEnable() {
        System.out.println("JsonNameWhitelist enabled!");

        getServer().getPluginManager().registerEvents(this, this);

        System.out.println("Registered all events!");
    }

    @Override
    public @NotNull ComponentLogger getComponentLogger() {
        return super.getComponentLogger();
    }

    @Override
    public void onDisable() {
        System.out.println("JsonNameWhitelist disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)  {
        Player player = event.getPlayer();
        String playerName = player.getName();

        JsonObject json = null;
        try {

            json = new JsonParser().parse(new FileReader(jsonFile)).getAsJsonObject();

        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        }

        JsonArray nameArray = json.get("playerNames").getAsJsonArray();
        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < nameArray.size(); i++) {
            nameList.add(nameArray.get(i).getAsString());
        }

        if (!nameList.contains(playerName)) {
            player.kick();
        }

    }
}
