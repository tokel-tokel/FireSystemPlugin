package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import ru.hse.edu.stalivanov.fireplugin.PluginMain;
import ru.hse.edu.stalivanov.fireplugin.SystemObjects;

public class SmokeDetector implements SerializableID
{
    private int x, y, z, x1, z1, x2, z2;
    private int system, group, id;
    private String worldName;
    private transient Util util;
    private transient SystemObjects collector = PluginMain.getSystemObjects();

    public void load()
    {
        util = new Util(worldName);
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
    public void remove()
    {

    }

    private class Util
    {
        private World world;

        public Util(String worldName)
        {
            world = Bukkit.getWorld(worldName);
        }
        
        public boolean checkCorrect()
        {
            var material = world.getBlockAt(x, y, z).getType();
            if(material != Material.QUARTZ_BLOCK)
            {
                collector.removeSmokeDetector(system, group, id);
                return false;
            }
            return true;
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
