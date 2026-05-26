package de.jaunikapauni.axworlds;

import de.jaunikapauni.axworlds.command.CreateCommand;
import de.jaunikapauni.axworlds.command.ListCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxWorlds extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("create").setExecutor(new CreateCommand());
        getCommand("list").setExecutor(new ListCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
