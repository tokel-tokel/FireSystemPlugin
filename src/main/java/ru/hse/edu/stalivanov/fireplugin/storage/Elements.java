package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.io.Serial;
import java.io.Serializable;
import java.util.TreeMap;

public class Elements implements GetAll, AddElement, Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private TreeMap<Integer, SerializableID> elements;

    public Elements()
    {
        elements = new TreeMap<>();
    }

    @Override
    public Iterable<SerializableID> getAll()
    {
        return elements.values();
    }

    @Override
    public boolean addElement(SerializableID element)
    {
        if(element.getID() <= 0)
            return false;
        return elements.putIfAbsent(element.getID(), element) == null;
    }

    public SerializableID getElement(int id)
    {
        return elements.get(id);
    }

    public boolean removeElement(int id)
    {
        return elements.remove(id) != null;
    }

    public int getFreeId()
    {
        return Util.getFreeInSet(elements.navigableKeySet());
    }

    public boolean checkId(int id)
    {
        return elements.containsKey(id);
    }
}
