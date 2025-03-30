package fr.black_eyes.glide;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Black_Eyes
 * Singleton-like class to initialize and get the config options
 *
 */
public class Config {




public Boolean
CheckForUpdates,
ConsoleMessages;

public String Glide_Permission;



/**
 *
 * -- GETTER --
 * return the only instance of the config options. Usefull for anything but reloading the plugin.
 */
@Getter
private static Config instance = null;


/**
 * Creates a new instance replacing the old one, and re-initialize the config options
 * Usefull for reloading the plugin
 * @param config The file to get the options from
 * @return a new instance of the config options
 */
public static Config getInstance(FileConfiguration config)
{
    instance = new Config(config);
    return instance;
}


    /**
 * Initialize all the config options by reading the YAML config file 
 * @param config The config file to get the options from, and initialize them
 */
public Config(FileConfiguration config) {
	CheckForUpdates = config.getBoolean("CheckForUpdates");
	ConsoleMessages = config.getBoolean("ConsoleMessages");
	Glide_Permission = config.getString("Glide_Permission");
}




	
}
