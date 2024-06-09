package ru.hse.edu.stalivanov.fireplugin.commands;

import org.bukkit.Material;

import java.util.List;
import java.util.TreeSet;

public interface Constants
{
    String toolLore = "FireSystem: a tool";
    TreeSet<Material> allowedMaterials = new TreeSet<>(List.of(Material.STICKY_PISTON, Material.QUARTZ_BLOCK, Material.LEVER));
}
