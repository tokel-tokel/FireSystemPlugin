package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.List;
import java.util.TreeSet;
import java.util.function.Supplier;

public class UtilBase
{
    protected World world;
    private int x, y, z;
    private final TreeSet<Material> allowedMaterials;

    public UtilBase(String worldName, int x, int y, int z, boolean inSystem, Material... materials)
    {
        world = Bukkit.getWorld(worldName);
        allowedMaterials = new TreeSet<>(List.of(materials));
        this.x = x; this.y = y; this.z = z;
    }

    public boolean checkCorrect()
    {
        Block block = getBlock();
        if(!allowedMaterials.contains(block.getType()))
            return false;
        return true;
    }

    protected Block getBlock()
    {
        return world.getBlockAt(x, y, z);
    }
}
