package org.giinger.chesttrader;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestTrader extends JavaPlugin {
	private static final Logger log = Logger.getLogger("Minecraft");
	public FileConfiguration config;
	public static Economy econ = null;

	@Override
	public void onDisable() {
		System.out.println("[ChestTrader] - Disabled!");
	}

	@Override
	public void onEnable() {
		System.out.println("[ChestTrader] - Enabled!");
		getServer().getPluginManager().registerEvents(new Sign(), this);
		getServer().getPluginManager().registerEvents(new PlayerL(), this);
		if (!setupEconomy() ) {
			log.info(String.format("[ChestTrader] Disabled due to Vault dependency not found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		loadConfiguration();
	}

	   private boolean setupEconomy() {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        econ = rsp.getProvider();
	        return econ != null;
	    }

	public void loadConfiguration() {
		config = getConfig();
		config.options().copyDefaults(true);
		String path = "Item.266.Price";
		config.addDefault(path, 30);
		config.options().copyDefaults(true);
		saveConfig();
	}


}
