package ru.hse.edu.stalivanov.fireplugin.storage;

import ru.hse.edu.stalivanov.fireplugin.funcblocks.SerializableID;

import java.util.LinkedList;
import java.util.List;

public interface GetAll
{
    Iterable<SerializableID> getAll();

    static Iterable<SerializableID> getAllByOther(Iterable<? extends GetAll> other)
    {
        List<SerializableID> res = new LinkedList<>();
        other.forEach(e -> e.getAll().forEach(res::add));
        return res;
    }
}
