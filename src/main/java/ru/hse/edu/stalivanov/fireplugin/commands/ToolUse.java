package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.block.Block;
import ru.hse.edu.stalivanov.fireplugin.PluginMain;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.Position;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

public class ToolUse
{
    private Storage storage;
    private ToolUseInteract interact;
    private ToolUseCreate create;

    public ToolUse(Storage storage)
    {
        this.storage = storage;
        interact = new ToolUseInteract();
        create = new ToolUseCreate(storage);
    }

    public void onUse(String playerName, Block block)
    {
        var element = storage.getElement(new Position(block));
        if(element == null)
            create.onUse(playerName, block);
        else
            interact.onUse(playerName, element);
    }
}
