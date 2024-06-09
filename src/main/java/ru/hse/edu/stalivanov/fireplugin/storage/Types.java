package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class Types implements GetAll, AddElement, Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private HashMap<ObjectTypes, Elements> elementsMap;

    public Types()
    {
        elementsMap = new HashMap<>();
        for(var t : ObjectTypes.values())
            elementsMap.put(t, new Elements());
    }

    @Override
    public Iterable<SerializableID> getAll()
    {
        return GetAll.getAllByOther(elementsMap.values());
    }

    @Override
    public boolean addElement(SerializableID element)
    {
        return elementsMap.get(element.getType()).addElement(element);
    }

    public SerializableID getElement(ObjectTypes type, int id)
    {
        return elementsMap.get(type).getElement(id);
    }

    public boolean removeElement(ObjectTypes type, int id)
    {
        return elementsMap.get(type).removeElement(id);
    }

    public Elements getElements(ObjectTypes type)
    {
        return elementsMap.get(type);
    }

    public int getFreeId(ObjectTypes type)
    {
        return elementsMap.get(type).getFreeId();
    }

    public boolean checkId(ObjectTypes type, int id)
    {
        return elementsMap.get(type).checkId(id);
    }
}
