package ru.hse.edu.stalivanov.fireplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GiveToolCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(!(commandSender instanceof Player player))
        {
            commandSender.sendMessage("Only player can execute this command!");
            return true;
        }
        ItemStack tool = new ItemStack(Material.COMPASS);
        tool.lore(List.of(Component.text(Constants.toolLore)));
        player.getInventory().addItem(tool);
        return true;
    }
}
