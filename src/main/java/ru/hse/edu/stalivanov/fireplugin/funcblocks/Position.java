package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class Position implements Comparable<Position>, Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private final int x, y, z;

    public Position(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(Block block)
    {
        this(block.getX(), block.getY(), block.getZ());
    }

    @Override
    public int compareTo(@NotNull Position position)
    {
        if(x > position.x)
            return 1;
        if(x < position.x)
            return -1;
        if(y > position.y)
            return 1;
        if(y < position.y)
            return -1;
        return Integer.compare(z, position.z);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }
}
