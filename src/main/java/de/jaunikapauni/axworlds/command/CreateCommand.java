package de.jaunikapauni.axworlds.command;

import de.jaunikapauni.axworlds.AxWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateCommand implements CommandExecutor {
    AxWorlds reference;
    public CreateCommand(AxWorlds reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        World newWorld = Bukkit.getServer().createWorld(WorldCreator.name(args[0]));
        Player p = (Player) sender;
        Location spawnLoc = newWorld.getSpawnLocation();
        spawnLoc.getChunk().load();
        p.teleport(spawnLoc);
        p.sendMessage("Your world " + args[0] + " has been created successfully!");
        reference.getConfig().set("worlds" + "." + args[0], true);
        reference.saveConfig();
        return true;
    }
}
