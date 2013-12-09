package de.daniel0916.KillMoney.API;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoneyGiveByPlayerEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
    private String message_;
    private String prefix_;
    private Player player_;
    private Player killer_;
    private double money_;
    private boolean cancelled;
    
    public PlayerMoneyGiveByPlayerEvent(Player killer, Player player, double money, String prefix, String message) {
    	message_ = message;
    	prefix_ = prefix;
    	player_ = player;
    	money_ = money;
    	killer_ = killer;
    }
    
    public String getMessage() {
        return message_;
    }
    
    public Player getKiller() {
        return killer_;
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
