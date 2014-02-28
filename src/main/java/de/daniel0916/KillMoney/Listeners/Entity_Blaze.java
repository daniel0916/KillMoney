package de.daniel0916.KillMoney.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import de.daniel0916.KillMoney.KillMoney;

public class Entity_Blaze implements Listener {
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity e = event.getEntity();
		EntityType en = event.getEntityType();
		if (!(event.getEntity().getKiller() instanceof Player)) {
			return;
		}
		Player Killer = event.getEntity().getKiller();
		
		if (en.equals(EntityType.BLAZE)) {
			KillMoney.HandleMoneyForMob(Killer, e);
		}
	}
}