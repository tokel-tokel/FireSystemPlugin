package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.Alarm;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.StickyPiston;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

public class AlarmCommand implements CommandExecutor
{
    private Storage storage;

    public AlarmCommand(Storage storage)
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
        Alarm alarm = (Alarm) storage.getElement(ObjectTypes.alarm, id);
        if(alarm == null)
            return true;
        if(strings[1].equalsIgnoreCase("activate"))
            alarm.turnOn();
        return true;
    }
}
