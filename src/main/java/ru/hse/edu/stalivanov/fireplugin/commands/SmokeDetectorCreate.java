package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SmokeDetector;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

public class SmokeDetectorCreate implements CreateHandler
{
    private Storage storage;
    private String playerName;
    private int x, y, z, x1, z1, x2, z2;
    private int step = 0;
    private ToolUseCreate tool;

    public SmokeDetectorCreate(String playerName, Storage storage, ToolUseCreate tool)
    {
        this.playerName = playerName;
        this.storage = storage;
        this.tool = tool;
    }

    @Override
    public void handleBlock(Block block)
    {
        if(step == 0 && block.getType() == Material.QUARTZ_BLOCK)
        {
            x = block.getX(); y = block.getY(); z = block.getZ();
            step += 1;
        }
        else if(step == 1)
        {
            x1 = block.getX(); z1 = block.getZ();
            step += 1;
        }
        else if(step == 2)
        {
            x2 = block.getX();
            z2 = block.getZ();
            SmokeDetector smokeDetector = new SmokeDetector(storage.getFreeId(ObjectTypes.smokeDetector),
                    x, y, z, x1, z1, x2, z2);
            if(storage.addElement(smokeDetector))
            {
                Bukkit.getPlayer(playerName).sendMessage("ID: " + smokeDetector.getID());
            }
            tool.removePlayer(playerName);
        }
    }
}
