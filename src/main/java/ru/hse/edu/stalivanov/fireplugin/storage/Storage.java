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

    private Types types;
    private Positions positions;

    public Storage()
    {
        types = new Types();
        positions = new Positions();
    }

    public boolean addElement(SerializableID element)
    {
        if(!types.addElement(element))
            return false;
        positions.add(element);
        return true;
    }

    private boolean removeElement(SerializableID el)
    {
        if(el == null)
            return false;
        types.removeElement(el.getType(), el.getID());
        positions.remove(el.getPosition());
        return true;
    }

    public boolean removeElement(ObjectTypes type, int id)
    {
        return removeElement(types.getElement(type, id));
    }

    public boolean removeElement(Position position)
    {
        return removeElement(positions.get(position));
    }

    public SerializableID getElement(ObjectTypes type, int id)
    {
        return types.getElement(type, id);
    }

    public SerializableID getElement(Position position)
    {
        return positions.get(position);
    }

    public Iterable<SerializableID> getAll()
    {
        return types.getAll();
    }

    public Iterable<SerializableID> getAllType(ObjectTypes type)
    {
        return types.getElements(type).getAll();
    }

    public int getFreeId(ObjectTypes type)
    {
        return types.getFreeId(type);
    }

    public boolean checkId(ObjectTypes type, int id)
    {
        return types.checkId(type, id);
    }
}
