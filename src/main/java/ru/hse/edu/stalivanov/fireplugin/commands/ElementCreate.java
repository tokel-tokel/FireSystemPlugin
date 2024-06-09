package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

import java.util.function.Supplier;

public class ElementCreate<T extends SerializableID> implements CreateHandler
{
    private Storage storage;
    private DefaultConstructor<T> constructor;
    private String playerName;
    private ObjectTypes type;
    private ToolUseCreate tool;

    public ElementCreate(String playerName, DefaultConstructor<T> constructor, ObjectTypes type, Storage storage, ToolUseCreate tool)
    {
        this.playerName = playerName;
        this.constructor = constructor;
        this.storage = storage;
        this.type = type;
        this.tool = tool;
    }

    @Override
    public void handleBlock(Block block)
    {
        T element = constructor.get(storage.getFreeId(type), block.getX(), block.getY(), block.getZ());
        if(!storage.addElement(element))
            return;
        Bukkit.getPlayer(playerName).sendMessage("ID: " + element.getID());
        tool.removePlayer(playerName);
    }

    public interface DefaultConstructor<T extends SerializableID>
    {
        T get(int id, int x, int y, int z);
    }
}
