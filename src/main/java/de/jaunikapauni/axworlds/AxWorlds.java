package de.jaunikapauni.axworlds;

import de.jaunikapauni.axworlds.command.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class AxWorlds extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("create").setExecutor(new CreateCommand(this));
        getCommand("create").setTabCompleter(new CreateTabCompleter());
        getCommand("list").setExecutor(new ListCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("teleport").setTabCompleter(new TeleportTabCompleter());
        getCommand("remove").setExecutor(new RemoveCommand(this));
        getCommand("remove").setTabCompleter(new RemoveTabCompleter());
        saveDefaultConfig();
        loadWorlds();
        getLogger().info("");
        getLogger().info("----------------------------------------");
        getLogger().info("Name: " + getName());
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info(String.join("Authors: " + ", ", getDescription().getAuthors()));
        getLogger().info("----------------------------------------");
        getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadWorlds(){
        if(getConfig().getConfigurationSection("worlds") != null){
            Set<String> worlds = getConfig().getConfigurationSection("worlds").getKeys(false);
            for(String w : worlds){
                String type = getConfig().getString("worlds." + w + ".type").toLowerCase();
                WorldCreator creator = WorldCreator.name(w);
                switch (type){
                    case "nether":
                        creator.environment(World.Environment.NETHER);
                        break;
                    case "end":
                        creator.environment(World.Environment.THE_END);
                        break;
                    case "empty":
                        creator.generator(new ChunkGenerator() {});
                        break;
                    case "normal":
                        creator.environment(World.Environment.NORMAL);
                        break;
                }
                Bukkit.createWorld(creator);
            }
        }
    }
}
