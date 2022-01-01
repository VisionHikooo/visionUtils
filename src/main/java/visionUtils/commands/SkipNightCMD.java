package visionUtils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.UUID;

public class SkipNightCMD implements CommandExecutor {

    private HashMap<UUID, Boolean> acceptList = new HashMap<>();

    /**
     * / Skip [accept | decline]
     * */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        return true;
    }
}
