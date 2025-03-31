package fr.black_eyes.glide;

import fr.black_eyes.glide.listeners.GlideListener;
import fr.black_eyes.glide.commands.GlideCommands;
import fr.black_eyes.simpleJavaPlugin.SimpleJavaPlugin;
import fr.black_eyes.simpleJavaPlugin.Updater;
import fr.black_eyes.simpleJavaPlugin.Utils;
import lombok.Getter;
import lombok.Setter;


public class Main extends SimpleJavaPlugin {

	@Setter @Getter private static Config configs;
	@Getter @Setter private static Main instance;
    
	public void onEnable() {
		setInstance(this);
		super.onEnable();
		if(configFiles.getLang() == null) {
			Utils.logInfo("&cConfig or data files couldn't be initialized, the plugin will stop.");
			return;
		}
		setCommandExecutor(new GlideCommands());

		this.getServer().getPluginManager().registerEvents(new GlideListener(), this);
        
		//load config
		setConfigs(Config.getInstance(configFiles.getConfig()));
		if(configs.CheckForUpdates) {
			new Updater(this, null);
		}

	}


}
