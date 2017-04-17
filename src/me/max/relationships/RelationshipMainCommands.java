package me.max.relationships;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by max on 16-4-2017.
 */
public class RelationshipMainCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "Console cannot use these commands!");
            return true;
        } else if (sender instanceof Player) {
            Player p = (Player) sender;
            if ((p.hasPermission("relationships.main")) && (args.length == 0)) {
                sender.sendMessage(ChatColor.BLUE + "|-| Relationships plugin v" + Relationship.getPlugin().getConfig().get("VERSION") + " |-|\n" +
                        "This plugin was made by Max|LemmoTresto|MaxiMiniJaniJos|Greyst\n" +
                        "For problems contact me!\n" +
                        "Github: github.com/MaxiMiniJaniJos\n" +
                        "Spigotmc: https://www.spigotmc.org/members/lemmotresto.284186\n" +
                        ChatColor.DARK_AQUA + "Use /relationships help <friend/marriage/love>");
                return true;
            } else if ((p.hasPermission("relationships.help.friend")) && (args[0].equalsIgnoreCase("help")) && (args[1].equalsIgnoreCase("friend"))) {
                sender.sendMessage(ChatColor.DARK_AQUA + "Relationships Help -|- Friend\n" + ChatColor.BLUE +
                        "/friend help - Shows this message\n" +
                        "/friend add - Sent a request to add someone as a friend\n" +
                        "/friend remove - Remove a friend from your friend list\n" +
                        "/friend pending <requests/sent/cancel> - Pending list\n" +
                        "/friend <accept/decline> Accept or decline a friend request\n" +
                        "/friend mail - Sends an mail to the player for if he is offline");
                return true;
            } else if ((p.hasPermission("relationships.help.marriage")) && (args[0].equalsIgnoreCase("help")) && (args[1].equalsIgnoreCase("marriage"))) {
                sender.sendMessage(ChatColor.DARK_AQUA + "Relationships Help -|- Marriage" +
                        ChatColor.BLUE + "- Empty -");
                return true;
            } else if ((p.hasPermission("relationships.help.love")) && (args[0].equalsIgnoreCase("help")) && (args[1].equalsIgnoreCase("love"))) {
                sender.sendMessage(ChatColor.DARK_AQUA + "Relationships Help -|- Love" +
                        ChatColor.BLUE + "- Empty -");
                return true;
            }

        }
        return false;
    }
}