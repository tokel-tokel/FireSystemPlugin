package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.Switch;

import java.io.Serial;

public class StickyPiston implements SerializableID
{
    @Serial
    private static final long serialVersionUID = 1;

    private int x, y, z;
    private transient Position position;
    private int id;
    private String worldName = "world";
    private transient Util util;

    public StickyPiston(int id, int x, int y, int z)
    {
        this.id = id; this.x = x; this.y = y; this.z = z;
        load();
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

    @Override
    public int getID()
    {
        return id;
    }

    @Override
    public ObjectTypes getType()
    {
        return ObjectTypes.stickyPiston;
    }

    public void setActivated(boolean b)
    {
        util.setActivated(b);
    }

    private class Util extends UtilBase
    {
        public Util(String worldName)
        {
            super(worldName, x, y, z, false, Material.LEVER);
        }

        public void setActivated(boolean b)
        {
            Switch piston = getPiston();
            piston.setPowered(!b);
            getBlock().setBlockData(piston);
        }

        private Switch getPiston()
        {
            BlockData bd = getBlock().getBlockData();
            if(bd instanceof Switch)
                return (Switch) bd;
            return null;
        }
    }
}
