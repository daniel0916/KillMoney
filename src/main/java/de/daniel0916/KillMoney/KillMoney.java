package de.daniel0916.KillMoney;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.daniel0916.KillMoney.API.PlayerMoneyGiveByPlayerEvent;
import de.daniel0916.KillMoney.API.PlayerMoneyGiveEvent;
import de.daniel0916.KillMoney.API.PlayerMoneyTakeByPlayerEvent;
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
		if (!setupEconomy()) {
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
		if (cmd.getName().equalsIgnoreCase("killmoney")) {
			if (args.length == 0){
				if (sender.hasPermission("killmoney.toogle")) {
					if (KillMoney.cfg.getBoolean("KillMoney.Enabled")) {
						cfg.set("KillMoney.Enabled", false);
						sender.sendMessage(prefix + ChatColor.GREEN + "KillMoney disabled");
						saveConfig();
						reloadConfig();
						
						return true;
					} else {
						cfg.set("KillMoney.Enabled", true);
						sender.sendMessage(prefix + ChatColor.GREEN + "KillMoney enabled");	
						saveConfig();
						reloadConfig();
						
						return true;
					}
				} else {
					sender.sendMessage("You don't have permissions for this command!");
					return true;
				}
			}
			return true;
			
		}
		return false;
	}
	
	
	public static void HandleMoneyForMob(Player Killer, EntityType Mob) {
		String w = Killer.getWorld().getName();
		String w1 = KillMoney.cfg.getString("KillMoney.Worlds.1");
		String w2 = KillMoney.cfg.getString("KillMoney.Worlds.2");
		String w3 = KillMoney.cfg.getString("KillMoney.Worlds.3");
		
		double Price = GetPriceFromEntityType(Mob);
		if (Price <= 0)
		{
			return;
		}
		
		if (KillMoney.cfg.getBoolean("KillMoney.Enabled")) {
			if (w.equals(w1)) {
				if (KillMoney.econ.hasAccount(Killer.getName())) {
					PlayerMoneyGiveEvent giveevent = new PlayerMoneyGiveEvent(Killer, Mob, Price, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Mob." + GetNameFromEntityType(Mob) + ".Message"));
					Bukkit.getServer().getPluginManager().callEvent(giveevent);
					if (!giveevent.isCancelled()) {
						KillMoney.econ.depositPlayer(Killer.getName(), giveevent.getMoney());
						if (KillMoney.cfg.getBoolean("KillMoney.Mob." + GetNameFromEntityType(Mob) + ".showMessage")) {
							Killer.sendMessage(ChatColor.translateAlternateColorCodes('&', giveevent.getPrefix() + giveevent.getMessage()));
						}
					}
				}
			}
			else if (w.equals(w2)) {
				if (KillMoney.econ.hasAccount(Killer.getName())) {
					PlayerMoneyGiveEvent giveevent = new PlayerMoneyGiveEvent(Killer, Mob, Price, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Mob." + GetNameFromEntityType(Mob) + ".Message"));
					Bukkit.getServer().getPluginManager().callEvent(giveevent);
					if (!giveevent.isCancelled()) {
						KillMoney.econ.depositPlayer(Killer.getName(), giveevent.getMoney());
						if (KillMoney.cfg.getBoolean("KillMoney.Mob." + GetNameFromEntityType(Mob) + ".showMessage")) {
							Killer.sendMessage(ChatColor.translateAlternateColorCodes('&', giveevent.getPrefix() + giveevent.getMessage()));
						}
					}
				}
			}
			else if (w.equals(w3)) {
				if (KillMoney.econ.hasAccount(Killer.getName())) {
					PlayerMoneyGiveEvent giveevent = new PlayerMoneyGiveEvent(Killer, Mob, Price, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Mob." + GetNameFromEntityType(Mob) + ".Message"));
					Bukkit.getServer().getPluginManager().callEvent(giveevent);
					if (!giveevent.isCancelled()) {
						KillMoney.econ.depositPlayer(Killer.getName(), giveevent.getMoney());
						if (KillMoney.cfg.getBoolean("KillMoney.Mob." + GetNameFromEntityType(Mob) + ".showMessage")) {
							Killer.sendMessage(ChatColor.translateAlternateColorCodes('&', giveevent.getPrefix() + giveevent.getMessage()));
						}
					}
				}
			}
		}
	}
	
	public static double GetPriceFromEntityType(EntityType Mob) {
		switch(Mob) {
			case BLAZE: return KillMoney.cfg.getDouble("KillMoney.Mob.Blaze.Price");
			case CAVE_SPIDER: return KillMoney.cfg.getDouble("KillMoney.Mob.Cave_Spider.Price");
			case CREEPER: return KillMoney.cfg.getDouble("KillMoney.Mob.Creeper.Price");
			case ENDER_DRAGON: return KillMoney.cfg.getDouble("KillMoney.Mob.Ender_Dragon.Price");
			case ENDERMAN: return KillMoney.cfg.getDouble("KillMoney.Mob.Enderman.Price");
			case GHAST: return KillMoney.cfg.getDouble("KillMoney.Mob.Ghast.Price");
			case GIANT: return KillMoney.cfg.getDouble("KillMoney.Mob.Giant.Price");
			case MAGMA_CUBE: return KillMoney.cfg.getDouble("KillMoney.Mob.Magma_Cube.Price");
			case PIG_ZOMBIE: return KillMoney.cfg.getDouble("KillMoney.Mob.Pig_Zombie.Price");
			case SILVERFISH: return KillMoney.cfg.getDouble("KillMoney.Mob.Silverfish.Price");
			case SKELETON: return KillMoney.cfg.getDouble("KillMoney.Mob.Skeleton.Price");
			case SLIME: return KillMoney.cfg.getDouble("KillMoney.Mob.Slime.Price");
			case SPIDER: return KillMoney.cfg.getDouble("KillMoney.Mob.Spider.Price");
			case WITCH: return KillMoney.cfg.getDouble("KillMoney.Mob.Witch.Price");
			case WITHER: return KillMoney.cfg.getDouble("KillMoney.Mob.Wither.Price");
			case ZOMBIE: return KillMoney.cfg.getDouble("KillMoney.Mob.Zombie.Price");
			
			default: return 0;
		}
	}
	
	public static String GetNameFromEntityType(EntityType Mob) {
		switch(Mob) {
			case BLAZE: return "Blaze";
			case CAVE_SPIDER: return "Cave_Spider";
			case CREEPER: return "Creeper";
			case ENDER_DRAGON: return "Ender_Dragon";
			case ENDERMAN: return "Enderman";
			case GHAST: return "Ghast";
			case GIANT: return "Giant";
			case MAGMA_CUBE: return "Magma_Cube";
			case PIG_ZOMBIE: return "Pig_Zombie";
			case SILVERFISH: return "Silverfish";
			case SKELETON: return "Skeleton";
			case SLIME: return "Slime";
			case SPIDER: return "Spider";
			case WITCH: return "Witch";
			case WITHER: return "Wither";
			case ZOMBIE: return "Zombie";
			
			default: return null;
		}
	}
	
	
	public static void HandleMoneyForPlayer(Player Killer, Player p) {
		double KillerPrice = KillMoney.cfg.getDouble("KillMoney.Player.KillerPrice");
		double DeathPrice = KillMoney.cfg.getDouble("KillMoney.Player.DeathPrice");
		double MinimumMoney = KillMoney.cfg.getDouble("KillMoney.Player.MinimumMoney");
		
		if (KillMoney.econ.hasAccount(p.getName())) {
			PlayerMoneyTakeByPlayerEvent takeevent = new PlayerMoneyTakeByPlayerEvent(p, Killer, DeathPrice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.DeathMessage"));
			Bukkit.getServer().getPluginManager().callEvent(takeevent);
			if (!takeevent.isCancelled()) {
				if (KillMoney.cfg.getBoolean("KillMoney.Player.MinimumMoneyEnabled")) {
					if (KillMoney.econ.bankBalance(p.getName()).balance - takeevent.getMoney() < MinimumMoney) {
						return;
					}
				}
				
				if (KillMoney.econ.bankBalance(p.getName()).balance < takeevent.getMoney()) {
					if (KillMoney.cfg.getBoolean("KillMoney.Player.AllowMinusMoneyByDeath")) {
						KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
						if (KillMoney.cfg.getBoolean("KillMoney.Player.showDeathMessage")) {
							String message = takeevent.getPrefix() + takeevent.getMessage();
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", Killer.getName())));
						}
					}
				} else {
					KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
					if (KillMoney.cfg.getBoolean("KillMoney.Player.showDeathMessage")) {
						String message = takeevent.getPrefix() + takeevent.getMessage();
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", Killer.getName())));
					}
				}
			}
		}
		
		if (KillMoney.econ.hasAccount(Killer.getName())) {
			PlayerMoneyGiveByPlayerEvent giveevent = new PlayerMoneyGiveByPlayerEvent(Killer, p, KillerPrice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.KillMessage"));
			Bukkit.getServer().getPluginManager().callEvent(giveevent);
			if (!giveevent.isCancelled()) {
				if (KillMoney.econ.bankBalance(p.getName()).balance < giveevent.getMoney()) {
					if (KillMoney.cfg.getBoolean("KillMoney.Player.KillMoneyWhenPlayerHaveNoMoney")) {
						KillMoney.econ.depositPlayer(Killer.getName(), giveevent.getMoney());
						if (KillMoney.cfg.getBoolean("KillMoney.Player.showKillMessage")) {
							String message = giveevent.getPrefix() + giveevent.getMessage();
							Killer.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
						}
					}
				} else {
					KillMoney.econ.depositPlayer(Killer.getName(), giveevent.getMoney());
					if (KillMoney.cfg.getBoolean("KillMoney.Player.showKillMessage")) {
						String message = giveevent.getPrefix() + giveevent.getMessage();
						Killer.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
					}
				}
			}
		}
	}
}