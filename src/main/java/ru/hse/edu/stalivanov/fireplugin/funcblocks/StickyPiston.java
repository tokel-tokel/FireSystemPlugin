package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Piston;

import java.io.Serial;

public class StickyPiston implements SerializableID
{
    @Serial
    private static final long serialVersionUID = 1;

    private int x, y, z;
    private transient Position position;
    private int system, group, id;
    private String worldName;
    private transient Util util;

    @Override
    public void load()
    {
        util = new Util(worldName);
        position = new Position(x, y, z);
    }

    @Override
    public Position getPosition()
    {
        return position;
    }

    @Override
    public int getID()
    {
        return id;
    }

    @Override
    public int getGroup()
    {
        return group;
    }

    @Override
    public int getSystem()
    {
        return system;
    }

    @Override
    public ObjectTypes getType()
    {
        return ObjectTypes.stickyPiston;
    }

    public void setActivated(boolean b)
    {

    }

    private class Util extends UtilBase
    {
        public Util(String worldName)
        {
            super(worldName, x, y, z, false, Material.STICKY_PISTON);
        }

        public void setActivated(boolean b)
        {
            getPiston().setExtended(!b);
        }

        private Piston getPiston()
        {
            BlockData bd = getBlock().getBlockData();
            if(bd instanceof Piston)
                return (Piston) bd;
            return null;
        }
    }
}
