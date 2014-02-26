package de.daniel0916.KillMoney.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.daniel0916.KillMoney.KillMoney;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (KillMoney.cfg.getString("KillMoney.Enable").equalsIgnoreCase("true")) {
			Player p = event.getEntity().getPlayer();
			Player killer = event.getEntity().getKiller();
			
			if (killer instanceof Player) {
				String w = killer.getWorld().getName();
				String w1 = KillMoney.cfg.getString("KillMoney.Worlds.1");
				String w2 = KillMoney.cfg.getString("KillMoney.Worlds.2");
				String w3 = KillMoney.cfg.getString("KillMoney.Worlds.3");
				if (w.equals(w1)) {
					KillMoney.HandleMoneyForPlayer(killer, p);
				}
				else if (w.equals(w2)) {
					KillMoney.HandleMoneyForPlayer(killer, p);
				}
				else if (w.equals(w3)) {
					KillMoney.HandleMoneyForPlayer(killer, p);
				}
			}
		}
	}
}