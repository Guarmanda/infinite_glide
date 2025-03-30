package fr.black_eyes.glide;

import fr.black_eyes.glide.listeners.GlideListener;
import org.bukkit.Bukkit;
import fr.black_eyes.glide.commands.GlideCommands;
import fr.black_eyes.simpleJavaPlugin.SimpleJavaPlugin;
import fr.black_eyes.simpleJavaPlugin.Utils;
import lombok.Getter;
import lombok.Setter;


public class Main extends SimpleJavaPlugin {

	@Setter @Getter private static Config configs;
	@Getter @Setter private static Main instance;
	@Getter private boolean essentials = false;
    
	public void onEnable() {
		setInstance(this);
		super.onEnable();
		if(configFiles.getLang() == null) {
			Utils.logInfo("&cConfig or data files couldn't be initialized, the plugin will stop.");
			return;
		}
		setCommandExecutor(new GlideCommands());

		if(Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials")){
        	Utils.logInfo("Hooked into essentials for mute");
        	essentials = true;
        }

		this.getServer().getPluginManager().registerEvents(new GlideListener(), this);
        
		//load config
		setConfigs(Config.getInstance(configFiles.getConfig()));

	}


}
