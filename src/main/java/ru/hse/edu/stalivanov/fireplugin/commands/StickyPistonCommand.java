package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.StickyPiston;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

public class StickyPistonCommand implements CommandExecutor
{
    private Storage storage;

    public StickyPistonCommand(Storage storage)
    {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length != 2)
            return false;
        int id;
        try {
            id = Integer.parseInt(strings[0]);
        } catch(NumberFormatException e) {
            return false;
        }
        StickyPiston stickyPiston = (StickyPiston) storage.getElement(ObjectTypes.stickyPiston, id);
        if(stickyPiston == null)
            return true;
        if(strings[1].equalsIgnoreCase("activate"))
            stickyPiston.setActivated(true);
        else if(strings[1].equalsIgnoreCase("deactivate"))
            stickyPiston.setActivated(false);
        return true;
    }
}
