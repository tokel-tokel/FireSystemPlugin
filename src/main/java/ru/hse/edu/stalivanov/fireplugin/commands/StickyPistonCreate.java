package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.StickyPiston;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

public class StickyPistonCreate implements CreateHandler
{
    private Storage storage;
    private String playerName;
    private ToolUseCreate tool;
    private int step = 0;

    public StickyPistonCreate(String playerName, Storage storage, ToolUseCreate tool)
    {
        this.storage = storage;
        this.playerName = playerName;
        this.tool = tool;
    }


    @Override
    public void handleBlock(Block block)
    {
        if(step == 0 && block.getType() == Material.STICKY_PISTON)
            step++;
        else if(step == 1 && block.getType() == Material.LEVER)
        {
            StickyPiston stickyPiston = new StickyPiston(storage.getFreeId(ObjectTypes.stickyPiston), block.getX(),
                    block.getY(), block.getZ());
            if(storage.addElement(stickyPiston))
            {
                Bukkit.getPlayer(playerName).sendMessage("ID: " + stickyPiston.getID());
                tool.removePlayer(playerName);
            }
        }
    }
}
