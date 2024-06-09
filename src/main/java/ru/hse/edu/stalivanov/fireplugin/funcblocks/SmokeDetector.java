package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.Material;

import java.io.Serial;

public class SmokeDetector implements SerializableID
{
    @Serial
    private static final long serialVersionUID = 1;

    private int x, y, z, x1, z1, x2, z2;
    private transient Position position;
    private int id;
    private String worldName = "world";
    private transient Util util;

    public SmokeDetector(int id, int x, int y, int z, int x1, int z1, int x2, int z2)
    {
        this.x = x; this.y = y; this.z = z; this.x1 = x1; this.z1 = z1; this.x2 = x2; this.z2 = z2; this.id = id;
        load();
    }

    @Override
    public void load()
    {
        util = new Util(worldName);
        position = new Position(x, y, z);
    }

    public String getArea()
    {
        int xmi = Math.min(x1, x2), xma = Math.max(x1, x2), zmi = Math.min(z1, z2), zma = Math.max(z1, z2);
        return String.format("(%d, %d)    (%d, %d)\n(%d, %d)    (%d, %d)", xmi, zma, xma, zma, xmi, zmi, xma, zmi);
    }

    @Override
    public Position getPosition()
    {
        return position;
    }

    public boolean isFire()
    {
        return util.isFire();
    }

    @Override
    public int getID()
    {
        return id;
    }

    @Override
    public ObjectTypes getType()
    {
        return ObjectTypes.smokeDetector;
    }

    private class Util extends UtilBase
    {
        public Util(String worldName)
        {
            super(worldName, x, y, z, false, Material.QUARTZ_BLOCK);
        }

        private boolean isFireAtPos(int xm, int zm)
        {
            int ym = y - 1;
            Material material = world.getBlockAt(xm, ym, zm).getType();
            while(material == Material.AIR || material == Material.CAVE_AIR)
            {
                material = world.getBlockAt(xm, --ym, zm).getType();
            }
            return material == Material.FIRE;
        }

        public boolean isFire()
        {
            for(int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++)
            {
                for(int j = Math.min(z1, z2); j <= Math.max(z1, z2); j++)
                {
                    if(isFireAtPos(i, j))
                        return true;
                }
            }
            return false;
        }
    }
}
