package de.jaunikapauni.axworlds.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axworlds.teleport")){
            p.sendMessage("You don't have the permission! [axworlds.teleport]");
            return true;
        }
        if(args.length < 1){
            return false;
        }
        World targetWorld = Bukkit.getWorld(args[0]);
        if(targetWorld == null){
            p.sendMessage("World doesn't exist");
            return true;
        }
        p.teleport(targetWorld.getSpawnLocation());
        p.sendMessage("You were successfully teleported to " + args[0]);
        return true;
    }
}
