package de.jaunikapauni.axworlds;

import de.jaunikapauni.axworlds.command.*;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class AxWorlds extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("create").setExecutor(new CreateCommand(this));
        getCommand("list").setExecutor(new ListCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("teleport").setTabCompleter(new TeleportTabCompleter());
        getCommand("remove").setExecutor(new RemoveCommand(this));
        getCommand("remove").setTabCompleter(new RemoveTabCompleter());
        saveDefaultConfig();
        loadWorlds();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadWorlds(){
        if(getConfig().getConfigurationSection("worlds") != null){
            Set<String> worlds = getConfig().getConfigurationSection("worlds").getKeys(false);
            for(String w : worlds){
                boolean empty = getConfig().getBoolean("worlds." + w + ".empty");
                WorldCreator creator = WorldCreator.name(w);
                if(empty){
                    creator.generator(new ChunkGenerator() {});
                }
                Bukkit.createWorld(creator);
            }
        }
    }
}
