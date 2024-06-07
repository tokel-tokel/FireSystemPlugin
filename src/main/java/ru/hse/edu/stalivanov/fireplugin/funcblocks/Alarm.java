package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Switch;

import java.io.Serial;

public class Alarm implements SerializableID
{
    @Serial
    private static final long serialVersionUID = 1;

    private int x, y, z;
    private transient Position position;
    private int system, id;
    private String worldName;
    private transient Util util;

    @Override
    public int getID()
    {
        return id;
    }

    @Override
    public int getGroup()
    {
        return 0;
    }

    @Override
    public int getSystem()
    {
        return system;
    }

    @Override
    public ObjectTypes getType()
    {
        return ObjectTypes.alarm;
    }


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

    private class Util extends UtilBase
    {
        public Util(String worldName)
        {
            super(worldName, x, y, z, true, Material.LEVER);
        }

        private Switch getSwitch()
        {
            BlockData bd = getBlock().getBlockData();
            if(bd instanceof Switch)
                return (Switch) bd;
            return null;
        }
    }
}
