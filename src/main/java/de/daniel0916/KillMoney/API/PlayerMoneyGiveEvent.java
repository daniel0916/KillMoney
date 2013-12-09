package de.daniel0916.KillMoney.API;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoneyGiveEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
    private String message_;
    private String prefix_;
    private Player player_;
    private EntityType monster_;
    private double money_;
    private boolean cancelled;
    
    public PlayerMoneyGiveEvent(Player player, EntityType monster, double money, String prefix, String message) {
    	message_ = message;
    	prefix_ = prefix;
    	player_ = player;
    	money_ = money;
    	monster_ = monster;
    }
    
    public String getMessage() {
        return message_;
    }
    
    public EntityType getMob() {
        return monster_;
    }
    
    public Player getPlayer() {
        return player_;
    }
    
    public double getMoney() {
        return money_;
    }
    
    public String getPrefix() {
        return prefix_;
    }
    
    public void setMessage(String message) {
        message_ = message;
    }
    
    public void setPrefix(String prefix) {
    	prefix_ = prefix;
    }
    
    public void setMoney(int money) {
        money_ = money;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
