package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.io.Serial;
import java.io.Serializable;
import java.util.TreeMap;

public class Systems implements GetAll, AddElement, Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private TreeMap<Integer, System> systems;

    public Systems()
    {
        systems = new TreeMap<>();
    }

    @Override
    public Iterable<SerializableID> getAll()
    {
        return GetAll.getAllByOther(systems.values());
    }

    @Override
    public boolean addElement(SerializableID element)
    {
        if(element.getSystem() <= 0)
            return false;
        if(!systems.containsKey(element.getSystem()))
            systems.put(element.getSystem(), new System());
        return systems.get(element.getSystem()).addElement(element);
    }

    public SerializableID getElement(int system, int group, ObjectTypes type, int id)
    {
        if(!systems.containsKey(system))
            return null;
        return systems.get(system).getElement(group, type, id);
    }

    public boolean removeElement(int system, int group, ObjectTypes type, int id)
    {
        if(!systems.containsKey(system))
            return false;
        return systems.get(system).removeElement(group, type, id);
    }

    public int getFreeSystem()
    {
        return Util.getFreeInSet(systems.navigableKeySet());
    }

    public int getFreeGroup(int system)
    {
        if(!systems.containsKey(system))
            return 0;
        return systems.get(system).getGroups().getFreeGroup();
    }

    public int getFreeId(int system, int group, ObjectTypes type)
    {
        var types = getGroup(system, group);
        if(types == null)
            return 0;
        return types.getElements(type).getFreeId();
    }

    public boolean checkSystem(int system)
    {
        return systems.containsKey(system);
    }

    public boolean checkGroup(int system, int group)
    {
        if(!checkSystem(system))
            return false;
        return systems.get(system).getGroups().checkGroup(group);
    }

    public boolean checkId(int system, int group, ObjectTypes type, int id)
    {
        if(!checkSystem(system))
            return false;
        if(group == 0)
            return systems.get(system).getTypes().getElements(type).checkId(id);
        if(!checkGroup(system, group))
            return false;
        return getGroup(system, group).getElements(type).checkId(id);
    }

    public boolean removeGroup(int system, int group)
    {
        if(!checkSystem(system))
            return false;
        return systems.get(system).getGroups().removeGroup(group);
    }

    public boolean removeSystem(int system)
    {
        return systems.remove(system) != null;
    }

    private Types getGroup(int system, int group)
    {
        try {
            return systems.get(system).getGroups().getTypes(group);
        } catch(NullPointerException e) {
            return null;
        }
    }

    public Iterable<SerializableID> getAllSystem(int system)
    {
        if(!systems.containsKey(system))
            return null;
        return systems.get(system).getAll();
    }

    public Iterable<SerializableID> getAllGroup(int system, int group)
    {
        var types = getGroup(system, group);
        if(types == null)
            return null;
        return types.getAll();
    }

    public Iterable<SerializableID> getAllGroupType(int system, int group, ObjectTypes type)
    {
        var types = getGroup(system, group);
        if(types == null)
            return null;
        return types.getElements(type).getAll();
    }

    public Iterable<SerializableID> getAllSysElements(int system)
    {
        if(!systems.containsKey(system))
            return null;
        return systems.get(system).getTypes().getAll();
    }

    public Iterable<SerializableID> getAllSysType(int system, ObjectTypes type)
    {
        if(!systems.containsKey(system))
            return null;
        return systems.get(system).getTypes().getElements(type).getAll();
    }

}
