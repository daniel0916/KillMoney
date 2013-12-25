package de.daniel0916.KillMoney.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.daniel0916.KillMoney.KillMoney;
import de.daniel0916.KillMoney.API.PlayerMoneyGiveByPlayerEvent;
import de.daniel0916.KillMoney.API.PlayerMoneyTakeByPlayerEvent;

public class PlayerListener implements Listener{
	
	String KillerPrice = "KillMoney.Player.KillerPrice";
	double killerprice = KillMoney.cfg.getDouble(KillerPrice);
	
	String DeathPrice = "KillMoney.Player.DeathPrice";
	double deathprice = KillMoney.cfg.getDouble(DeathPrice);
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(KillMoney.cfg.getString("KillMoney.Enable").equalsIgnoreCase("true")) {
			Player p = event.getEntity().getPlayer();
			Player killer = event.getEntity().getKiller();
			
			if (killer instanceof Player) {
				String w = killer.getWorld().getName();
				String w1 = KillMoney.cfg.getString("KillMoney.Worlds.1");
				String w2 = KillMoney.cfg.getString("KillMoney.Worlds.2");
				String w3 = KillMoney.cfg.getString("KillMoney.Worlds.3");
				if(w.equals(w1)) {
					if(KillMoney.econ.hasAccount(killer.getName())) {
						PlayerMoneyGiveByPlayerEvent giveevent = new PlayerMoneyGiveByPlayerEvent(killer, p, killerprice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.KillMessage"));
						Bukkit.getServer().getPluginManager().callEvent(giveevent);
						if (!giveevent.isCancelled()) {
							if (KillMoney.econ.bankBalance(p.getName()).balance < giveevent.getMoney()) {
								if (KillMoney.cfg.getBoolean("KillMoney.Player.KillMoneyWhenPlayerHaveNoMoney")) {
									KillMoney.econ.depositPlayer(killer.getName(), giveevent.getMoney());
									if (KillMoney.cfg.getString("KillMoney.Player.showKillMessage").equalsIgnoreCase("on")) {
										String message = giveevent.getPrefix() + giveevent.getMessage();
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
									}
								}
							} else {
								KillMoney.econ.depositPlayer(killer.getName(), giveevent.getMoney());
								if (KillMoney.cfg.getString("KillMoney.Player.showKillMessage").equalsIgnoreCase("on")) {
									String message = giveevent.getPrefix() + giveevent.getMessage();
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
								}
							}
						}
					}
					
					if(KillMoney.econ.hasAccount(p.getName())) {
						PlayerMoneyTakeByPlayerEvent takeevent = new PlayerMoneyTakeByPlayerEvent(p, killer, deathprice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.DeathMessage"));
						Bukkit.getServer().getPluginManager().callEvent(takeevent);
						if (!takeevent.isCancelled()) {
							if (KillMoney.econ.bankBalance(p.getName()).balance < takeevent.getMoney()) {
								if (KillMoney.cfg.getBoolean("KillMoney.Player.AllowMinusMoneyByDeath")) {
									KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
									if (KillMoney.cfg.getString("KillMoney.Player.showDeathMessage").equalsIgnoreCase("on")) {
										String message = takeevent.getPrefix() + takeevent.getMessage();
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", killer.getName())));
									}
								}
							} else {
								KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
								if (KillMoney.cfg.getString("KillMoney.Player.showDeathMessage").equalsIgnoreCase("on")) {
									String message = takeevent.getPrefix() + takeevent.getMessage();
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", killer.getName())));
								}
							}
						}
					}
				}
				else if(w.equals(w2)) {
					if(KillMoney.econ.hasAccount(killer.getName())) {
						PlayerMoneyGiveByPlayerEvent giveevent = new PlayerMoneyGiveByPlayerEvent(killer, p, killerprice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.KillMessage"));
						Bukkit.getServer().getPluginManager().callEvent(giveevent);
						if (!giveevent.isCancelled()) {
							if (KillMoney.econ.bankBalance(p.getName()).balance < giveevent.getMoney()) {
								if (KillMoney.cfg.getBoolean("KillMoney.Player.KillMoneyWhenPlayerHaveNoMoney")) {
									KillMoney.econ.depositPlayer(killer.getName(), giveevent.getMoney());
									if (KillMoney.cfg.getString("KillMoney.Player.showKillMessage").equalsIgnoreCase("on")) {
										String message = giveevent.getPrefix() + giveevent.getMessage();
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
									}
								}
							} else {
								KillMoney.econ.depositPlayer(killer.getName(), giveevent.getMoney());
								if (KillMoney.cfg.getString("KillMoney.Player.showKillMessage").equalsIgnoreCase("on")) {
									String message = giveevent.getPrefix() + giveevent.getMessage();
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
								}
							}
						}
					}
					
					if(KillMoney.econ.hasAccount(p.getName())) {
						PlayerMoneyTakeByPlayerEvent takeevent = new PlayerMoneyTakeByPlayerEvent(p, killer, deathprice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.DeathMessage"));
						Bukkit.getServer().getPluginManager().callEvent(takeevent);
						if (!takeevent.isCancelled()) {
							if (KillMoney.econ.bankBalance(p.getName()).balance < takeevent.getMoney()) {
								if (KillMoney.cfg.getBoolean("KillMoney.Player.AllowMinusMoneyByDeath")) {
									KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
									if (KillMoney.cfg.getString("KillMoney.Player.showDeathMessage").equalsIgnoreCase("on")) {
										String message = takeevent.getPrefix() + takeevent.getMessage();
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", killer.getName())));
									}
								}
							} else {
								KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
								if (KillMoney.cfg.getString("KillMoney.Player.showDeathMessage").equalsIgnoreCase("on")) {
									String message = takeevent.getPrefix() + takeevent.getMessage();
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", killer.getName())));
								}
							}
						}
					}
				}
				else if(w.equals(w3)) {
					if(KillMoney.econ.hasAccount(killer.getName())) {
						PlayerMoneyGiveByPlayerEvent giveevent = new PlayerMoneyGiveByPlayerEvent(killer, p, killerprice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.KillMessage"));
						Bukkit.getServer().getPluginManager().callEvent(giveevent);
						if (!giveevent.isCancelled()) {
							if (KillMoney.econ.bankBalance(p.getName()).balance < giveevent.getMoney()) {
								if (KillMoney.cfg.getBoolean("KillMoney.Player.KillMoneyWhenPlayerHaveNoMoney")) {
									KillMoney.econ.depositPlayer(killer.getName(), giveevent.getMoney());
									if (KillMoney.cfg.getString("KillMoney.Player.showKillMessage").equalsIgnoreCase("on")) {
										String message = giveevent.getPrefix() + giveevent.getMessage();
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
									}
								}
							} else {
								KillMoney.econ.depositPlayer(killer.getName(), giveevent.getMoney());
								if (KillMoney.cfg.getString("KillMoney.Player.showKillMessage").equalsIgnoreCase("on")) {
									String message = giveevent.getPrefix() + giveevent.getMessage();
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Player>", p.getName())));
								}
							}
						}
					}
					
					if(KillMoney.econ.hasAccount(p.getName())) {
						PlayerMoneyTakeByPlayerEvent takeevent = new PlayerMoneyTakeByPlayerEvent(p, killer, deathprice, KillMoney.prefix, KillMoney.cfg.getString("KillMoney.Player.DeathMessage"));
						Bukkit.getServer().getPluginManager().callEvent(takeevent);
						if (!takeevent.isCancelled()) {
							if (KillMoney.econ.bankBalance(p.getName()).balance < takeevent.getMoney()) {
								if (KillMoney.cfg.getBoolean("KillMoney.Player.AllowMinusMoneyByDeath")) {
									KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
									if (KillMoney.cfg.getString("KillMoney.Player.showDeathMessage").equalsIgnoreCase("on")) {
										String message = takeevent.getPrefix() + takeevent.getMessage();
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", killer.getName())));
									}
								}
							} else {
								KillMoney.econ.withdrawPlayer(p.getName(), takeevent.getMoney());
								if (KillMoney.cfg.getString("KillMoney.Player.showDeathMessage").equalsIgnoreCase("on")) {
									String message = takeevent.getPrefix() + takeevent.getMessage();
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<Killer>", killer.getName())));
								}
							}
						}
					}
				}
			}
		}
	}

}
