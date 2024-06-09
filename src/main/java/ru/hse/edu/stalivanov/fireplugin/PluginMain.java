package ru.hse.edu.stalivanov.fireplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hse.edu.stalivanov.fireplugin.commands.*;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

import java.io.*;


public class PluginMain extends JavaPlugin
{
    private Storage storage;

    @Override
    public void onLoad()
    {
        getDataFolder().mkdirs();
        File file = new File(getDataFolder(), "storage.ser");
        boolean createNew = true;
        if(file.exists() && file.isFile() && file.canRead())
        {
            try(FileInputStream fis = new FileInputStream(file))
            {
                ObjectInputStream ois = new ObjectInputStream(fis);
                storage = (Storage) ois.readObject();
                createNew = false;
                ois.close();
            }
            catch(Exception ignored)
            {
                Bukkit.getLogger().info("Serialization read error");
            }
        }
        if(createNew)
            storage = new Storage();
    }

    @Override
    public void onEnable()
    {
        storage.getAll().forEach(SerializableID::load);
        getServer().getPluginManager().registerEvents(new ToolTouch(storage), this);
        getServer().getPluginManager().registerEvents(new DestroyBlock(storage), this);
        getCommand("firesystem").setExecutor(new FireSystemCommand(storage));
        getCommand("givetool").setExecutor(new GiveToolCommand());
        getCommand("smokedetector").setExecutor(new SmokeDetectorCommand(storage));
        getCommand("stickypiston").setExecutor(new StickyPistonCommand(storage));
        getCommand("alarm").setExecutor(new AlarmCommand(storage));
    }

    @Override
    public void onDisable()
    {
        File file = new File(getDataFolder(), "storage.ser");
        try(FileOutputStream fos = new FileOutputStream(file))
        {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(storage);
            oos.close();
        }
        catch(IOException e)
        {
            Bukkit.getLogger().info(file.getPath());
            Bukkit.getLogger().info(e.getMessage());
            Bukkit.getLogger().info("Serialization write error");
        }
    }
}
