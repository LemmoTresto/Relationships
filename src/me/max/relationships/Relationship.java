package me.max.relationships;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.management.relation.Relation;

/**
 * Created by max on 15-4-2017.
 */
public class Relationship extends JavaPlugin {

    public static Relationship getPlugin() {
        return getPlugin(Relationship.class);
    }

    @Override
    public void onEnable() {

        FileConfiguration config = getConfig();

        getLogger().info("Reading/Writing config..");
        try {
            config.addDefault("VERSION", "0.1");
            config.addDefault("Relationships", "There is no need to touch anything from this config file. Things you're allowed to touch if needed is enableFriendCommands, enableMarriageCommands, enableLoveCommands. Do not touch any of the rest.");
            config.addDefault("enableFriendCommands", true);
            config.addDefault("enableMarriageCommands", true);
            config.addDefault("enableLoveCommands", true);
            config.options().copyDefaults(true);
            saveConfig();
            getLogger().info("Successfully read/wrote config.");
        } catch (Exception e) {
            getLogger().info("Error in reading/writing config. Please restart the server. \nIf this persists contact developer!");
            getPluginLoader().disablePlugin(this);
        }

        getLogger().info("Enabling commands..");

        try {
            this.getCommand("relationships").setExecutor(new RelationshipMainCommands());
            if (config.getBoolean("enableFriendCommands")) {
                this.getCommand("friend").setExecutor(new RelationshipFriendCommands());
                getLogger().info("Enabled Friend commands.");
            } else if (!(config.getBoolean("enableFriendCommands"))) {
                getLogger().info("Not enabling Friend commands. If you do want these commands enable them in the config file.");
            }

            if (config.getBoolean("enableMarriageCommands")) {
                //this.getCommand("marriage").setExecutor(new RelationshipMarriageCommands());
                getLogger().info("Enabled Marriage commands.");
            } else if (!(config.getBoolean("enableMarriageCommands"))) {
                getLogger().info("Not enabling Marriage commands. If you do want these commands enable them in the config file.");
            }

            if (config.getBoolean("enableLoveCommands")) {
                //this.getCommand("love").setExecutor(new RelationshipLoveCommands());
                getLogger().info("Enabled Love commands.");
            } else if (!(config.getBoolean("enableLoveCommands"))) {
                getLogger().info("Not enabling Love commands. If you do want these commands enable them in the config file.");
            }

        } catch (Exception e) {
            getLogger().info("Error in enabling commands restart server.\nif this persists please contact dev.");
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }


    }

    @Override
    public void onDisable() {

        getLogger().info("Disabling Commands..");
        getLogger().info("Successfully disabled commands.");
        getLogger().info("Succesfully disabled plugin.");
    }
}

/*
/friend add
/friend list
/friend pending <requests/sent/cancel>
/friend msg
/friend mail <list/sent>
/friend remove
/friend accept/decline
 */