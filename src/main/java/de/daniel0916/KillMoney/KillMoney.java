package de.daniel0916.KillMoney;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.daniel0916.KillMoney.Listeners.Entity_Blaze;
import de.daniel0916.KillMoney.Listeners.Entity_CaveSpider;
import de.daniel0916.KillMoney.Listeners.Entity_Creeper;
import de.daniel0916.KillMoney.Listeners.Entity_EnderDragon;
import de.daniel0916.KillMoney.Listeners.Entity_Enderman;
import de.daniel0916.KillMoney.Listeners.Entity_Ghast;
import de.daniel0916.KillMoney.Listeners.Entity_Giant;
import de.daniel0916.KillMoney.Listeners.Entity_MagmaCube;
import de.daniel0916.KillMoney.Listeners.Entity_PigZombie;
import de.daniel0916.KillMoney.Listeners.Entity_Silverfish;
import de.daniel0916.KillMoney.Listeners.Entity_Skeleton;
import de.daniel0916.KillMoney.Listeners.Entity_Slime;
import de.daniel0916.KillMoney.Listeners.Entity_Spider;
import de.daniel0916.KillMoney.Listeners.Entity_Witch;
import de.daniel0916.KillMoney.Listeners.Entity_Wither;
import de.daniel0916.KillMoney.Listeners.Entity_Zombie;
import de.daniel0916.KillMoney.Listeners.PlayerListener;

public class KillMoney extends JavaPlugin{
	
	public File pluginfile = new File("plugins", "KillMoney.jar");
	
	public static File file = new File("plugins/KillMoney", "config.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static Permission perms = null;
	public static Economy econ = null;
	
	public static String prefix = null;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		try {
			cfg.load(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		prefix = ChatColor.translateAlternateColorCodes('&', KillMoney.cfg.getString("KillMoney.Prefix"));
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
			System.out.println("[KillMoney] Can't load Metrics.");
		}
		
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		this.getServer().getPluginManager().registerEvents(new Entity_Blaze(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_CaveSpider(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Creeper(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_EnderDragon(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Enderman(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Ghast(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Giant(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_MagmaCube(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_PigZombie(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Silverfish(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Skeleton(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Slime(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Spider(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Witch(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Wither(), this);
		this.getServer().getPluginManager().registerEvents(new Entity_Zombie(), this);
		
		loadVault();
	}
	
	private void loadVault() {
		if (!setupEconomy() ) {
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "[KillMoney] No Vault found");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setupPermission();
	}
			
	private boolean setupPermission() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
		

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("killmoney")) {
			if (args.length == 0){
				if(sender.hasPermission("killmoney.toogle")) {
					if(cfg.getString("KillMoney.Enable").equalsIgnoreCase("true")) {
						cfg.set("KillMoney.Enable", "false");
						sender.sendMessage(prefix + ChatColor.GREEN + "KillMoney disabled");
						saveConfig();
						reloadConfig();
						
						return true;
					} else {
						cfg.set("KillMoney.Enable", "true");
						sender.sendMessage(prefix + ChatColor.GREEN + "KillMoney enabled");	
						saveConfig();
						reloadConfig();
						
						return true;
					}
				}else{
					sender.sendMessage("You don't have permissions for this command!");
					return true;
				}
			}
			return true;
			
		}
		return false;
	}
	
}