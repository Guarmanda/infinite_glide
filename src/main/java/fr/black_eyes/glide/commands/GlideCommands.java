package fr.black_eyes.glide.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import fr.black_eyes.glide.Config;
import fr.black_eyes.glide.Main;
import fr.black_eyes.simpleJavaPlugin.Files;
import fr.black_eyes.simpleJavaPlugin.SimpleJavaPlugin;
import fr.black_eyes.simpleJavaPlugin.Utils;
import org.bukkit.entity.Player;


public class GlideCommands implements CommandExecutor, TabCompleter  {

	private final SimpleJavaPlugin main;
	private final Files configFiles;


	public GlideCommands() {
		main = Main.getInstance();
		configFiles = main.getConfigFiles();
	}
	


	 
	//variables for command completion
	private static final String[] completions0 = {"allow", "deny", "reload"};
		
	 
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

			
			if (args.length > 0 && !hasPerm(sender, args[0])) {
				return false;
			}

			if(args.length ==2) {
				Player p = main.getServer().getPlayer(args[1]);
				if(p == null) {
					Utils.msg(sender, "PlayerNotFound", "[Player]", args[1]);
					return false;
				}
				switch(args[0]) {
					case "allow":
						if(p.hasPermission(Main.getConfigs().Glide_Permission)){
							Utils.msg(sender, "PlayerAlreadyHasPermission", "[Player]", args[1]);
							return false;
						}
						p.addAttachment(main, Main.getConfigs().Glide_Permission, true);
						Utils.msg(sender, "PlayerAddedPermission", "[Player]", args[1]);
						break;
					case "deny":
						if(!p.hasPermission(Main.getConfigs().Glide_Permission)){
							Utils.msg(sender, "PlayerDoesNotHavePermission", "[Player]", args[1]);
							return false;
						}
						p.addAttachment(main, Main.getConfigs().Glide_Permission, false);
						Utils.msg(sender, "PlayerRemovedPermission", "[Player]", args[1]);
						break;
					default:
						displayHelp(sender);
				}
			}
			else if(args.length == 1) {

				if(args[0].equalsIgnoreCase("reload")) {
					configFiles.reloadData();
					configFiles.reloadConfig();
					Main.setConfigs(Config.getInstance(configFiles.getConfig()));
					Utils.msg(sender, "PluginReloaded", " ", " ");
				}
				else {
					displayHelp(sender);
				}
			}
			else {
				displayHelp(sender);
			}
		
		return false;
	}
	
	public void displayHelp(CommandSender p) {
		List<String> help = configFiles.getLang().getStringList("help");
        for (String s : help) {
            p.sendMessage(Utils.color(s));
        }
	}

	boolean hasPerm(CommandSender sender, String permission) {
		if (!sender.hasPermission(main.getName() + "." + permission) && !sender.hasPermission(main.getName() + ".admin") && !sender.hasPermission(main.getName() + ".*")) {
			Utils.msg(sender, "noPermission", "[Permission]", main.getName() + "." + permission);
			return false;
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {
		final List<String> completions = new ArrayList<>();
		if(args.length == 1){
		    for(String string : completions0){
		        if(string.toLowerCase().startsWith(args[0].toLowerCase())) completions.add(string);
		    }
		    return completions;
		}
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("allow")){
				//for each player
				for(Player p : Bukkit.getOnlinePlayers()){
					if(!p.hasPermission(Main.getConfigs().Glide_Permission)){
						completions.add(p.getName());
					}
				}
			}else if(args[0].equalsIgnoreCase("deny")){
				for(Player p : Bukkit.getOnlinePlayers()){
					if(p.hasPermission(Main.getConfigs().Glide_Permission)){
						completions.add(p.getName());
					}
				}
			}


		}
		return null;
	}
	
	
}
