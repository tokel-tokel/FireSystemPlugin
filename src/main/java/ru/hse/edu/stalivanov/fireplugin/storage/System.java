package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class System implements GetAll, AddElement, Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private Groups groups;
    private Types types;

    @Override
    public Iterable<SerializableID> getAll()
    {
        return GetAll.getAllByOther(List.of(groups, types));
    }

    @Override
    public boolean addElement(SerializableID element)
    {
        if(element.getGroup() == 0)
            return types.addElement(element);
        return groups.addElement(element);
    }

    public SerializableID getElement(int group, ObjectTypes type, int id)
    {
        if(group == 0)
            return types.getElement(type, id);
        return groups.getElement(group, type, id);
    }

    public boolean removeElement(int group, ObjectTypes type, int id)
    {
        if(group == 0)
            return types.removeElement(type, id);
        return groups.removeElement(group, type, id);
    }

    public Groups getGroups()
    {
        return groups;
    }

    public Types getTypes()
    {
        return types;
    }
}
