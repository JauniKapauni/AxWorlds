package de.jaunikapauni.axworlds.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axworlds.list")){
            p.sendMessage("You don't have the permission! [axworlds.list]");
            return true;
        }
        p.sendMessage("Loaded worlds:");
        for(World world : Bukkit.getWorlds()){
            p.sendMessage("- " + world.getName());
        }
        return true;
    }
}
