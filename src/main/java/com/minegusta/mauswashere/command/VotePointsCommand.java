package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.data.VotePointsDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.Set;
import java.util.UUID;

public class VotePointsCommand extends WrappedCommand {

    public VotePointsCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("votepoints");
    }

    @Override
    public boolean processCommand(CommandSender s, Command cmd, String[] args) {
        if(cmd.getName().equalsIgnoreCase("votepoints")){
            if(args.length < 2);
            try{

                UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();

                if(args[0].equalsIgnoreCase("add")){
                    VotePointsDataManager.addPoint(uuid);

                }
                else if(args[0].equalsIgnoreCase("reset")){
                    VotePointsDataManager.resetPoint(uuid);
                }


                }catch(Exception e){

            }
        }
        return true;
    }
}