package ru.hse.edu.stalivanov.fireplugin.commands;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.Position;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

public class DestroyBlock implements Listener
{
    private Storage storage;

    public DestroyBlock(Storage storage)
    {
        this.storage = storage;
    }

    @EventHandler
    public void onDestroy(BlockDestroyEvent event)
    {
        Block block = event.getBlock();
        storage.removeElement(new Position(block));
        if(block.getType() == Material.QUARTZ_BLOCK)
            Bukkit.getLogger().info(String.format("%d %d %d", block.getX(), block.getY(), block.getZ()));
    }
}
