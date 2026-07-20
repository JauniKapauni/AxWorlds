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
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class CreateCommand implements CommandExecutor {
    AxWorlds reference;
    public CreateCommand(AxWorlds reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axworlds.create")){
            p.sendMessage("You don't have the permission! [axworlds.create]");
            return true;
        }
        if(args.length < 2){
            return false;
        }
        WorldCreator creator = WorldCreator.name(args[0]);
        String type = args[1].toLowerCase();
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
            default:
                p.sendMessage("Unkown world type");
                return true;
        }
        if(Bukkit.getWorld(args[0]) != null){
            p.sendMessage("World already exists!");
            return true;
        }
        World newWorld = Bukkit.createWorld(creator);
        if(newWorld == null){
            p.sendMessage("Failed to create world.");
            return true;
        }
        Location spawnLoc = newWorld.getSpawnLocation();
        p.teleport(spawnLoc);

        p.sendMessage("Your world " + args[0] + " has been created successfully!");

        reference.getConfig().set("worlds." + args[0] + ".type", type);
        reference.saveConfig();
        return true;
    }
}
