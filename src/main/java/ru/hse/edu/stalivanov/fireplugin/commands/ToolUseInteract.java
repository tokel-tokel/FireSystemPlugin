package ru.hse.edu.stalivanov.fireplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.Bukkit;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SmokeDetector;

public class ToolUseInteract
{
    public void onUse(String playerName, SerializableID element)
    {
        createHandler(element, playerName).onInteract();
    }

    private InteractHandler createHandler(SerializableID element, String player)
    {
        if(element.getType() == ObjectTypes.smokeDetector)
            return new SmokeDetectorInteract(player, (SmokeDetector) element);
        return new ElementInteract(player, element);
    }

    private interface InteractHandler
    {
        void onInteract();
    }

    private static class SmokeDetectorInteract implements InteractHandler
    {
        private String player;
        private SmokeDetector smokeDetector;

        public SmokeDetectorInteract(String player, SmokeDetector smokeDetector)
        {
            this.smokeDetector = smokeDetector;
            this.player = player;
        }

        @Override
        public void onInteract()
        {
            Bukkit.getPlayer(player).sendMessage(Component.join(JoinConfiguration.newlines(),
                    Component.text("Info: ID: " + smokeDetector.getID()), Component.text("Area:"),
                    Component.text(smokeDetector.getArea())));
        }
    }

    private static class ElementInteract implements InteractHandler
    {
        private String player;
        private SerializableID element;

        public ElementInteract(String player, SerializableID element)
        {
            this.player = player;
            this.element = element;
        }

        @Override
        public void onInteract()
        {
            Bukkit.getPlayer(player).sendMessage(Component.text("Type: " + element.getType() + " ID: " + element.getID()));
        }
    }
}
