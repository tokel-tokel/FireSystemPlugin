package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Switch;

import java.io.Serial;
import java.util.Objects;

public class Alarm implements SerializableID
{
    @Serial
    private static final long serialVersionUID = 1;

    private int x, y, z;
    private transient Position position;
    private int id;
    private String worldName = "world";
    private transient Util util;

    public Alarm(int id, int x, int y, int z)
    {
        this.id = id; this.x = x; this.y = y; this.z = z;
        load();
    }

    @Override
    public int getID()
    {
        return id;
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

    public void turnOn()
    {
        util.turnOn();
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

        public void turnOn()
        {
            Switch sw = getSwitch();
            sw.setPowered(true);
            getBlock().setBlockData(sw);
        }
    }
}
