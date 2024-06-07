package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.ObjectTypes;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.Position;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.io.Serial;
import java.io.Serializable;

public class Storage implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1;

    private Systems systems;
    private Positions positions;

    public boolean addElement(SerializableID element)
    {
        if(!systems.addElement(element))
            return false;
        positions.add(element);
        return true;
    }

    public boolean removeElement(Position pos)
    {
        var el = positions.get(pos);
        if(el == null)
            return false;
        positions.remove(pos);
        systems.removeElement(el.getSystem(), el.getGroup(), el.getType(), el.getID());
        return true;
    }

    public boolean removeElement(int system, int group, ObjectTypes type, int id)
    {
        var el = systems.getElement(system, group, type, id);
        if(el == null)
            return false;
        systems.removeElement(system, group, type, id);
        positions.remove(el.getPosition());
        return true;
    }

    public SerializableID getElement(Position pos)
    {
        return positions.get(pos);
    }

    public SerializableID getElement(int system, int group, ObjectTypes type, int id)
    {
        return systems.getElement(system, group, type, id);
    }

    public int getFreeSystem()
    {
        return systems.getFreeSystem();
    }

    public int getFreeGroup(int system)
    {
        return systems.getFreeGroup(system);
    }

    public int getFreeId(int system, int group, ObjectTypes type)
    {
        return systems.getFreeId(system, group, type);
    }

    public Iterable<SerializableID> getAll()
    {
        return systems.getAll();
    }

    public boolean checkSystem(int system)
    {
        return systems.checkSystem(system);
    }

    public boolean checkGroup(int system, int group)
    {
        return systems.checkGroup(system, group);
    }

    public boolean checkId(int system, int group, ObjectTypes type, int id)
    {
        return systems.checkId(system, group, type, id);
    }

    public boolean removeGroup(int system, int group)
    {
        return systems.removeGroup(system, group);
    }

    public boolean removeSystem(int system)
    {
        return systems.removeSystem(system);
    }

    public Iterable<SerializableID> getAllSystem(int system)
    {
        return systems.getAllSystem(system);
    }

    public Iterable<SerializableID> getAllGroup(int system, int group)
    {
        return systems.getAllGroup(system, group);
    }

    public Iterable<SerializableID> getAllGroupType(int system, int group, ObjectTypes type)
    {
        return systems.getAllGroupType(system, group, type);
    }

    public Iterable<SerializableID> getAllSysElements(int system)
    {
        return systems.getAllSysElements(system);
    }

    public Iterable<SerializableID> getAllSysType(int system, ObjectTypes type)
    {
        return systems.getAllSysType(system, type);
    }
}
