package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.io.Serial;
import java.io.Serializable;
import java.util.TreeMap;

public class Groups implements GetAll, AddElement, Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private TreeMap<Integer, Types> groups;

    @Override
    public Iterable<SerializableID> getAll()
    {
        return GetAll.getAllByOther(groups.values());
    }

    @Override
    public boolean addElement(SerializableID element)
    {
        if(element.getGroup() <= 0)
            return false;
        if(!groups.containsKey(element.getGroup()))
            groups.put(element.getGroup(), new Types());
        return groups.get(element.getGroup()).addElement(element);
    }

    public SerializableID getElement(int group, ObjectTypes type, int id)
    {
        if(!groups.containsKey(group))
            return null;
        return groups.get(group).getElement(type, id);
    }

    public boolean removeElement(int group, ObjectTypes type, int id)
    {
        if(!groups.containsKey(group))
            return false;
        return groups.get(group).removeElement(type, id);
    }

    public Types getTypes(int group)
    {
        return groups.get(group);
    }

    public int getFreeGroup()
    {
        return Util.getFreeInSet(groups.navigableKeySet());
    }

    public boolean checkGroup(int group)
    {
        return groups.containsKey(group);
    }

    public boolean removeGroup(int group)
    {
        return groups.remove(group) != null;
    }

}
