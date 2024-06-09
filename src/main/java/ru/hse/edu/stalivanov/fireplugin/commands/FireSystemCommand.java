package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

import java.util.TreeMap;

public class FireSystemCommand implements CommandExecutor
{
    private static TreeMap<String, ObjectTypes> typesMap;
    static
    {
        typesMap = new TreeMap<>(String::compareToIgnoreCase);
        for(var t : ObjectTypes.values())
            typesMap.put(t.toString(), t);
    }

    private Storage storage;

    public FireSystemCommand(Storage storage)
    {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length != 3)
            return false;
        if(!strings[0].equalsIgnoreCase("remove"))
            return false;
        if(!typesMap.navigableKeySet().contains(strings[1]))
            return false;
        var type = typesMap.get(strings[1]);
        int id;
        try {
            id = Integer.parseInt(strings[2]);
        } catch(NumberFormatException e) {
            return false;
        }
        if(storage.removeElement(type, id))
            commandSender.sendMessage("Successfully removed");
        else
            commandSender.sendMessage("Cannot remove!");
        return true;
    }
}
