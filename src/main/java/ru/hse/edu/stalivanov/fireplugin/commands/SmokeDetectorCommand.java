package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SmokeDetector;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

public class SmokeDetectorCommand implements CommandExecutor
{
    private Storage storage;

    public SmokeDetectorCommand(Storage storage)
    {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length != 1)
            return false;
        int id;
        try {
            id = Integer.parseInt(strings[0]);
        } catch(NumberFormatException e) {
            return false;
        }
        SmokeDetector smokeDetector = (SmokeDetector) storage.getElement(ObjectTypes.smokeDetector, id);
        if(smokeDetector == null)
        {
            commandSender.sendMessage("error");
            return true;
        }
        boolean isSmoke = smokeDetector.isFire();
        if(isSmoke)
            commandSender.sendMessage("true");
        else
            commandSender.sendMessage("false");
        return true;
    }
}
