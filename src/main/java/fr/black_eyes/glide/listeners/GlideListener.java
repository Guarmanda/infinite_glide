package fr.black_eyes.glide.listeners;


import fr.black_eyes.glide.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class GlideListener implements Listener  {
	

	@EventHandler
	public void onGlideToggle(EntityToggleGlideEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(p.hasPermission(Main.getConfigs().Glide_Permission) && e.isGliding() ){
				e.setCancelled(true);
			}
		}
	}

	//on fall
	@EventHandler
	public void onPlayerFall(PlayerMoveEvent e){
		if(!e.getPlayer().isGliding())
			if(e.getFrom().getY() > e.getTo().getY()) {
				// check if block at y-2 is air
				Location loc = e.getFrom().clone();
				loc.setY(loc.getY() -1.5);
				if (loc.getBlock().getType() == Material.AIR && e.getPlayer().hasPermission(Main.getConfigs().Glide_Permission) ) {
					e.getPlayer().setGliding(true);
				}
			}

	}

	//cancel fall damage if permission
	@EventHandler
	public void onFallDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
				Player p = (Player) e.getEntity();
				if(p.hasPermission(Main.getConfigs().Glide_Permission)){
					e.setCancelled(true);
				}
			}
		}
	}


		
}
