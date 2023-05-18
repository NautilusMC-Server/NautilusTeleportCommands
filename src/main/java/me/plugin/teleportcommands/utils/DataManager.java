package me.plugin.teleportcommands.utils;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {

    private static final String CONFIG_NAME = "data.yml";
    private TeleportCommands plugin;
    private FileConfiguration config;
    private File configFile;

    public DataManager(TeleportCommands plugin) {
        this.plugin = plugin;

        saveDefaultConfig();
        reloadConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), CONFIG_NAME);
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = plugin.getResource(CONFIG_NAME);

        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) reloadConfig();
        return config;
    }

    public void saveConfig() {
        if (config == null || configFile == null) return;

        try {
            getConfig().save(configFile);
        }
        catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save to config " + configFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), CONFIG_NAME);
        }

        if (!configFile.exists()) {
            plugin.saveResource(CONFIG_NAME, false);
        }
    }

    //accessor methods
    //config
    public int getMaxHomes() {
        return getConfig().getConfigurationSection("config").getInt("maxHomes");
    }

    //other
    public void checkPlayer(Player p) {
        if(!getConfig().getKeys(false).contains(p.getUniqueId())) {
            config.createSection(p.getUniqueId() + ".homes");
            saveConfig();
        }
    }

    public boolean hasHome(Player p, String name) {
        ConfigurationSection cfg = getConfig().getConfigurationSection(p.getUniqueId() + ".homes");
        return cfg.getKeys(false).contains(name);
    }

    public Location getHomeLocation(Player p, String name) {
        ConfigurationSection cfg = getConfig().getConfigurationSection(p.getUniqueId() + ".homes");
        return cfg.getLocation(name);
    }

    public void addHome(Player p, String name) {
        ConfigurationSection cfg = getConfig().getConfigurationSection(p.getUniqueId() + ".homes");
        //check if player alr has home of that name
        if(cfg.getKeys(false).contains(name)) {
            p.sendMessage(ChatColor.GRAY + "You already have a home of that name.");
            return;
        }
        //check if player alr has [maxHomes] homes
        if(cfg.getKeys(false).size() > getMaxHomes()) {
            p.sendMessage(ChatColor.GRAY + "You already have the maximum number of homes.");
            return;
        }
        //add to config
        cfg.set(name, p.getLocation());
        p.sendMessage(ChatColor.AQUA + "New home added!");
        saveConfig();
    }

    public void deleteHome(Player p, String name) {
        ConfigurationSection cfg = getConfig().getConfigurationSection(p.getUniqueId() + ".homes");
        //check if player alr has home of that name
        if(cfg.getKeys(false).contains(name)) {
            p.sendMessage(ChatColor.GRAY + "You already have a home of that name.");
            return;
        }
        //check if player alr has [maxHomes] homes
        if(cfg.getKeys(false).size() > getMaxHomes()) {
            p.sendMessage(ChatColor.GRAY + "You already have the maximum number of homes.");
            return;
        }
        //add to config
        cfg.set(name, p.getLocation());
        p.sendMessage(ChatColor.AQUA + "New home added!");
        saveConfig();
    }

}