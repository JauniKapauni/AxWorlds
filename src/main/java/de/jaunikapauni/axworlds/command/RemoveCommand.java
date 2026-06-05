package de.jaunikapauni.axworlds.command;

import de.jaunikapauni.axworlds.AxWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RemoveCommand implements CommandExecutor {
    AxWorlds reference;
    public RemoveCommand(AxWorlds reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        World world = Bukkit.getWorld(args[0]);
        World defaultWorld = Bukkit.getWorlds().get(0);
        Location defaultSpawnLocation = defaultWorld.getSpawnLocation();
        for(Player p : world.getPlayers()){
            p.teleport(defaultSpawnLocation);
        }
        Bukkit.unloadWorld(world, true);
        File worldFolder = world.getWorldFolder();
        deleteFolder(worldFolder);
        sender.sendMessage("You have successfully removed " + args[0]);
        reference.getConfig().set("worlds" + "." + args[0], null);
        reference.saveConfig();
        return true;
    }

    public void deleteFolder(File directory){
        File[] allContents = directory.listFiles();
        if(allContents != null){
            for(File file : allContents){
                deleteFolder(file);
            }
        }
        directory.delete();
    }
}
