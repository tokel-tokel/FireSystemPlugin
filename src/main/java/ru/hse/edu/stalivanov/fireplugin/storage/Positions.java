package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.Position;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.io.Serial;
import java.io.Serializable;
import java.util.TreeMap;

public class Positions implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private TreeMap<Position, SerializableID> positions;

    public Positions()
    {
        positions = new TreeMap<>();
    }

    public void add(SerializableID element)
    {
        positions.put(element.getPosition(), element);
    }

    public boolean remove(Position pos)
    {
        return positions.remove(pos) != null;
    }

    public SerializableID get(Position pos)
    {
        return positions.get(pos);
    }
}
