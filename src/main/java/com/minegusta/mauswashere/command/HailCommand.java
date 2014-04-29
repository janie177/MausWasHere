package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class HailCommand extends WrappedCommand
{

	public HailCommand() {
		super(false);
	}

	@Override
	public Set<String> getCommands() {
		return Sets.newHashSet("hail");
	}

	@Override
	public boolean processCommand(CommandSender sender, Command command, String[] args) {
		if (command.getName().equalsIgnoreCase("hail") && args.length > 0 && sender.isOp()) {
			String hail = "";
			for(int number = 0; number < args.length; number++ )
			{
				hail = hail + " " + args[number];
			}
			for(Player p : Bukkit.getOnlinePlayers())
			{
				p.chat(ChatColor.RED + "ALL HAIL " + ChatColor.YELLOW + hail + ChatColor.RED + "!");
			}
			return true;
		}
		return false;
	}
}
