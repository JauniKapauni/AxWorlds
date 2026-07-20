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
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axworlds.remove")){
            p.sendMessage("You don't have the permission! [axworlds.remove]");
            return true;
        }
        World world = Bukkit.getWorld(args[0]);
        World defaultWorld = Bukkit.getWorlds().get(0);
        Location defaultSpawnLocation = defaultWorld.getSpawnLocation();
        for(Player player : world.getPlayers()){
            player.teleport(defaultSpawnLocation);
        }
        Bukkit.unloadWorld(world, true);
        File worldFolder = world.getWorldFolder();
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            deleteFolder(worldFolder);
        });
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
