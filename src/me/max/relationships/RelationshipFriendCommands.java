package me.max.relationships;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
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
            File file = new File(Relationship.getPlugin().getDataFolder(), "data/" + ((Player) sender).getUniqueId().toString() + ".yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            File file2 = new File(Relationship.getPlugin().getDataFolder(), "data/" + Bukkit.getPlayer(args[1]).getUniqueId().toString() + ".yml");
            FileConfiguration data2 = YamlConfiguration.loadConfiguration(file2);
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
            } else if ((sender.hasPermission("relationships.friend.add")) && args[0].equalsIgnoreCase("add")) {
                if (args.length >= 2) {
                    if (data.contains("PendingRequest")) {
                        ArrayList FriendList = (ArrayList) data.getList("PendingRequest");
                        ArrayList FriendList2 = (ArrayList) data.getList("FriendList");
                        if (FriendList.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString()) || FriendList2.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
                            sender.sendMessage(ChatColor.BLUE + "You've already sent a request or are already friends with that person!");
                            return true;
                        } else if (data.contains("PendingSent")) {
                            ArrayList sentList = (ArrayList) data.getList("PendingSent");
                            if (sentList.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
                                sender.sendMessage(ChatColor.BLUE + "That person has already sent an request to you!\n" +
                                        "Use /friend accept");
                                return true;
                            }
                            else {
                                //do nothing ;)
                        }
                        } else if (!(FriendList.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString()))) {
                            FriendList.add(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                            data.set("PendingRequest", FriendList);
                            if (data2.contains("PendingSent")) {
                                ArrayList SentList = (ArrayList) data2.getList("PendingSent");
                                if (!SentList.contains(((Player) sender).getUniqueId().toString())) {
                                    SentList.add(((Player) sender).getUniqueId().toString());
                                    data2.set("PendingSent", SentList);
                                } else {
                                    //do nothing.
                                }
                            } else {
                                ArrayList SentList = new ArrayList();
                                SentList.add(((Player) sender).getUniqueId().toString());
                                data2.set("PendingSent", SentList);
                            }
                            try {
                                data.save(file);
                                data2.save(file2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Bukkit.getPlayer(args[1]).sendMessage(ChatColor.BLUE + sender.getName().toString() + " has asked you to become friends. type /friend accept " + sender.getName().toString());
                            sender.sendMessage(ChatColor.BLUE + "Successfully sent a friend request to " + args[1]);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "Something went wrong, that's all we know :(");
                            return true;
                        }
                    } else {
                        if (data.contains("PendingSent")) {
                            ArrayList sentList = (ArrayList) data.getList("PendingSent");
                            if (sentList.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString())) {
                                sender.sendMessage(ChatColor.BLUE + "That person has already sent an request to you!\n" +
                                        "Use /friend accept");
                                return true;
                            }
                            else {
                                //do nothing :)
                            }
                        }
                        ArrayList FriendList = new ArrayList();
                        FriendList.add(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                        data.set("PendingRequest", FriendList);
                        if (data2.contains("PendingSent")) {
                            ArrayList SentList = (ArrayList) data2.getList("PendingSent");
                            if (!SentList.contains(((Player) sender).getUniqueId().toString())) {
                                SentList.add(((Player) sender).getUniqueId().toString());
                                data2.set("PendingSent", SentList);
                            } else {
                                //do nothing.
                            }
                        } else {
                            ArrayList SentList = new ArrayList();
                            SentList.add(((Player) sender).getUniqueId().toString());
                            data2.set("PendingSent", SentList);
                        }

                        try {
                            data.save(file);
                            data2.save(file2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bukkit.getPlayer(args[1]).sendMessage(ChatColor.BLUE + sender.getName().toString() + " has asked you to become friends. type /friend accept " + sender.getName().toString());
                        sender.sendMessage(ChatColor.BLUE + "Succesfully sent a friend request to " + args[1]);
                        return true;
                    }
                }
            } else if (args[0].equalsIgnoreCase("accept") && sender.hasPermission("relationships.friend.accept")){
                ArrayList SentList = (ArrayList) data.getList("PendingSent");
                ArrayList SentList2 = (ArrayList) data2.getList("PendingRequest");
                if (SentList.contains(Bukkit.getPlayer(args[1]).getUniqueId().toString()) && SentList2.contains(((Player) sender).getUniqueId().toString())){
                    if ((data.contains("FriendList"))){
                        ArrayList FriendList = (ArrayList) data.getList("FriendList");
                        FriendList.add(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                        data.set("FriendList", FriendList);
                    }
                    else if (!data.contains("FriendList")){
                        ArrayList FriendList = new ArrayList();
                        FriendList.add(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                        data.set("FriendList", FriendList);
                    }
                    if (data2.contains("FriendList")){
                        ArrayList FriendList2 = (ArrayList) data2.getList("FriendList");
                        FriendList2.add(((Player) sender).getUniqueId().toString());
                        data2.set("FriendList", FriendList2);
                    }
                    else if (!(data2.contains("FriendList"))){
                        ArrayList FriendList = new ArrayList();
                        FriendList.add(((Player) sender).getUniqueId().toString());
                        data2.set("FriendList", FriendList);
                    }
                    ArrayList PendingSent = (ArrayList) data.get("PendingSent");
                    ArrayList PendingRequest = (ArrayList) data2.get("PendingRequest");
                    PendingSent.remove(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                    PendingRequest.remove(((Player) sender).getUniqueId().toString());
                    data.set("PendingSent", PendingSent);
                    data2.set("PendingRequest", PendingRequest);
                    try {
                        data.save(file);
                        data2.save(file2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage(ChatColor.BLUE + "You are now friends with " + Bukkit.getPlayer(args[1]).getName().toString());
                    Bukkit.getPlayer(args[1]).sendMessage(ChatColor.BLUE + sender.getName().toString() + " has accepted your friend request. You are now friends!");
                    return true;
                }
            }

        }

        return false;
    }
}