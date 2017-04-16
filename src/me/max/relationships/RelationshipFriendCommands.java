package me.max.relationships;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by max on 15-4-2017.
 */
public class RelationshipFriendCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "Console cannot use these commands!");
            return true;
        } else if (sender instanceof Player) {
            if ((args[0].equalsIgnoreCase("help")) && (sender.hasPermission("relationships.help.friend"))) {
                sender.sendMessage(ChatColor.DARK_AQUA + "Relationships Help -|- Friend\n" + ChatColor.BLUE +
                        "/friend help - Shows this message\n" +
                        "/friend add - Sent a request to add someone as a friend\n" +
                        "/friend remove - Remove a friend from your friend list\n" +
                        "/friend pending <requests/sent/cancel> - Pending list\n" +
                        "/friend <accept/decline> Accept or decline a friend request\n" +
                        "/friend mail - Sends an mail to the player for if he is offline");
                return true;
            }
            if ((sender.hasPermission("relationships.friend.main")) && (args.length == 0)) {
                sender.sendMessage(ChatColor.BLUE + "This is no command, were you looking for /friend help?");
                return true;
            } else if ((sender.hasPermission("relationships.friend.add")) && args[0].equalsIgnoreCase("add"))
                if (args.length >= 2) {
                    FileConfiguration data = YamlConfiguration.loadConfiguration(new File(Relationship.getPlugin().getDataFolder(), "data/" + ((Player) sender).getUniqueId().toString() + ".yml"));
                    if (data.contains(sender.getName().toString() + "PendingRequests")) {
                        ArrayList FriendList = (ArrayList) data.getList(sender.getName().toString() + "PendingRequests");
                        if (FriendList.contains(args[1])) {
                            sender.sendMessage(ChatColor.BLUE + "You've already sent a request or are already friends with that person!");
                            return true;
                        } else if (!(FriendList.contains(args[1]))) {
                            FriendList.add(args[1]);
                            data.set(sender.getName().toString() + "PendingRequests", FriendList);
                            sender.sendMessage(ChatColor.BLUE + "Successfully sent a friend request to " + args[1]);
                            Relationship.getPlugin().saveConfig();
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "Something went wrong, that's all we know :(");
                            return true;
                        }
                    } else {
                        ArrayList FriendList = new ArrayList();
                        FriendList.add(args[1]);
                        data.set(sender.getName().toString() + "PendingRequests", FriendList);
                        data.set(sender.getName().toString() + "PendingSent", "");
                        data.set(sender.getName().toString() + "FriendList", "");
                        data.set(sender.getName().toString() + "UnreadMail", "");
                        Relationship.getPlugin().saveConfig();
                        sender.sendMessage(ChatColor.BLUE + "Succesfully sent a friend request to " + args[1]);
                        return true;
                    }
                }
        }
        return false;
    }
}
