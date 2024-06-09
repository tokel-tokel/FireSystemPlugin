package ru.hse.edu.stalivanov.fireplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.hse.edu.stalivanov.fireplugin.storage.Storage;

import java.util.List;


public class ToolTouch implements Listener
{
    private ToolUse toolUse;

    public ToolTouch(Storage storage)
    {
        toolUse = new ToolUse(storage);
    }

    @EventHandler
    public void onToolTouch(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(!event.hasItem() || !event.hasBlock())
            return;

        ItemStack item = event.getItem();
        if(item.getType() != Material.COMPASS)
            return;

        Block block = event.getClickedBlock();
        if(!block.getWorld().getName().equalsIgnoreCase("world"))
            return;

        List<Component> lore = item.lore();
        if(lore == null)
            return;
        boolean b = false;
        for(var c : lore)
            if(c instanceof TextComponent textComponent)
                if(textComponent.content().equals(Constants.toolLore))
                {
                    b = true;
                    break;
                }
        if(!b) return;

        event.setCancelled(true);
        toolUse.onUse(player.getName(), block);
    }
}
