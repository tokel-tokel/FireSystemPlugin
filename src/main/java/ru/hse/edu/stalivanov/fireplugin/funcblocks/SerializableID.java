package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface SerializableID extends Serializable, Comparable<SerializableID>
{
    int getID();

    void remove();

    @Override
    default int compareTo(@NotNull SerializableID o)
    {
        return Integer.compare(getID(), o.getID());
    }
}
