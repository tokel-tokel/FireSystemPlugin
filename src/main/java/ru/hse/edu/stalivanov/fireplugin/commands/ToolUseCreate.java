package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.Alarm;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.StickyPiston;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

import java.util.HashMap;

public class ToolUseCreate
{
    private HashMap<String, CreateHandler> players;
    private Storage storage;

    public ToolUseCreate(Storage storage)
    {
        players = new HashMap<>();
        this.storage = storage;
    }

    public void removePlayer(String playerName)
    {
        players.remove(playerName);
    }

    public void onUse(String playerName, Block block)
    {
        if(!players.containsKey(playerName))
        {
            var handler = createHandler(block.getType(), playerName);
            if(handler == null)
                return;
            players.put(playerName, handler);
        }
        players.get(playerName).handleBlock(block);
    }

    private CreateHandler createHandler(Material material, String playerName)
    {
        CreateHandler res;
        switch(material)
        {
            case STICKY_PISTON -> res = new StickyPistonCreate(playerName, storage, this);
            case QUARTZ_BLOCK -> res = new SmokeDetectorCreate(playerName, storage, this);
            case LEVER -> res = new ElementCreate<>(playerName, Alarm::new, ObjectTypes.alarm, storage, this);
            default -> res = null;
        }
        return res;
    }
}
