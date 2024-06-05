package ru.hse.edu.stalivanov.fireplugin;

import com.sun.source.tree.Tree;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import ru.hse.edu.stalivanov.fireplugin.funcblocks.*;

import java.io.Serializable;
import java.util.*;

public class SystemObjects implements Serializable
{
    //{System: [{Group: {Name: {ID: Object}}}, {Name: {ID: Object}}]}
    private TreeMap<Integer, Pair<TreeMap<Integer, HashMap<ObjectTypes, TreeMap<Integer, SerializableID>>>, HashMap<SysObjectTypes, TreeMap<Integer, SerializableID>>>> systems;

    public boolean checkSystem(int system)
    {
        return systems.containsKey(system);
    }

    public boolean checkGroup(int system, int group)
    {
        if(!checkSystem(system))
            return false;
        return systems.get(system).getLeft().containsKey(group);
    }

    public boolean checkID(int system, int group, ObjectTypes type, int id)
    {
        if(!checkGroup(system, group))
            return false;
        return systems.get(system).getLeft().get(group).get(type).containsKey(id);
    }

    public boolean checkSysID(int system, SysObjectTypes type, int id)
    {
        if(!checkSystem(system))
            return false;
        return systems.get(system).getRight().get(type).containsKey(id);
    }

    private boolean checkAddSystem(int system)
    {
        if(!systems.containsKey(system))
        {
            HashMap<SysObjectTypes, TreeMap<Integer, SerializableID>> nameMap = new HashMap<>();
            for(var s : SysObjectTypes.values())
                nameMap.put(s, new TreeMap<>());
            systems.put(system, new MutablePair<>(new TreeMap<>(), nameMap));
            return false;
        }
        return true;
    }

    private boolean checkAddGroup(int system, int group)
    {
        checkAddSystem(system);
        var groupMap = systems.get(system).getLeft();
        if(!groupMap.containsKey(group))
        {
            HashMap<ObjectTypes, TreeMap<Integer, SerializableID>> nameMap = new HashMap<>();
            for(var s : ObjectTypes.values())
                nameMap.put(s, new TreeMap<>());
            groupMap.put(group, nameMap);
            return false;
        }
        return true;
    }

    private void addObject(int system, int group, ObjectTypes type, SerializableID obj)
    {
        checkAddGroup(system, group);
        systems.get(system).getLeft().get(group).get(type).put(obj.getID(), obj);
    }

    private void addSysObject(int system, SysObjectTypes type, SerializableID obj)
    {
        checkAddSystem(system);
        systems.get(system).getRight().get(type).put(obj.getID(), obj);
    }

    public void addSmokeDetector(int system, int group, SmokeDetector obj)
    {
        addObject(system, group, ObjectTypes.smokeDetector, obj);
    }

    public void addStickyPiston(int system, int group, StickyPiston obj)
    {
        addObject(system, group, ObjectTypes.stickyPiston, obj);
    }

    public void addAlarm(int system, Alarm obj)
    {
        addSysObject(system, SysObjectTypes.alarm, obj);
    }

    private boolean removeObject(int system, int group, ObjectTypes type, int id)
    {
        if(!checkID(system, group, type, id))
            return false;
        var map = systems.get(system).getLeft().get(group).get(type);
        map.get(id).remove();
        return map.remove(id) != null;
    }

    private boolean removeSysObject(int system, SysObjectTypes type, int id)
    {
        if(!checkSysID(system, type, id))
            return false;
        var map = systems.get(system).getRight().get(type);
        map.get(id).remove();
        return map.remove(id) != null;
    }

    public boolean removeSmokeDetector(int system, int group, int id)
    {
        return removeObject(system, group, ObjectTypes.smokeDetector, id);
    }

    public boolean removeStickyPiston(int system, int group, int id)
    {
        return removeObject(system, group, ObjectTypes.stickyPiston, id);
    }

    public boolean removeAlarm(int system, int id)
    {
        return removeSysObject(system, SysObjectTypes.alarm, id);
    }

    private Collection<SerializableID> getObjects(int system, int group, ObjectTypes type)
    {
        if(!checkGroup(system, group))
            return null;
        return systems.get(system).getLeft().get(group).get(type).values();
    }

    private Collection<SerializableID> getSysObjects(int system, SysObjectTypes type)
    {
        if(!checkSystem(system))
            return null;
        return systems.get(system).getRight().get(type).values();
    }

    private <T extends SerializableID> Iterable<T> castCollection(Collection<SerializableID> objects, Class<T> toCast)
    {
        if(objects == null)
            return null;
        List<T> result = new ArrayList<>();
        for(var o : objects)
            result.add((T) o);
        return result;
    }

    public Iterable<SmokeDetector> getSmokeDetectors(int system, int group)
    {
        return castCollection(getObjects(system, group, ObjectTypes.smokeDetector), SmokeDetector.class);
    }

    public Iterable<StickyPiston> getStickyPistons(int system, int group)
    {
        return castCollection(getObjects(system, group, ObjectTypes.stickyPiston), StickyPiston.class);
    }

    public Iterable<Alarm> getAlarms(int system)
    {
        return castCollection(getSysObjects(system, SysObjectTypes.alarm), Alarm.class);
    }

    private int getFreeInSet(NavigableSet<Integer> set)
    {
        if(set.first() == null)
            return 1;
        int first = set.first();
        Integer second = set.higher(first);
        while(second != null && second - first <= 1)
        {
            first = second;
            second = set.higher(first);
        }
        return first + 1;
    }

    public int getFreeSystem()
    {
        var keys = systems.navigableKeySet();
        return getFreeInSet(keys);
    }

    public int getFreeGroup(int system)
    {
        if(!checkSystem(system))
            return -1;
        var keys = systems.get(system).getLeft().navigableKeySet();
        return getFreeInSet(keys);
    }

    public int getFreeID(int system, int group, ObjectTypes type)
    {
        if(!checkGroup(system, group))
            return -1;
        var keys = systems.get(system).getLeft().get(group).get(type).navigableKeySet();
        return getFreeInSet(keys);
    }

    public int getSysFreeID(int system, SysObjectTypes type)
    {
        if(!checkSystem(system))
            return -1;
        var keys = systems.get(system).getRight().get(type).navigableKeySet();
        return getFreeInSet(keys);
    }

    public boolean removeSystem(int system)
    {
        if(!checkSystem(system))
            return false;
        for(int group : systems.get(system).getLeft().navigableKeySet())
            removeGroup(system, group);
        for(var i : systems.get(system).getRight().values())
            for(var j : i.values())
                j.remove();
        return systems.remove(system) != null;
    }

    public boolean removeGroup(int system, int group)
    {
        if(!checkGroup(system, group))
            return false;
        var objects = systems.get(system).getLeft().get(group).values();
        for(var i : objects)
            for(var j : i.values())
                j.remove();
        return systems.get(system).getLeft().remove(group) != null;
    }

    private <T extends SerializableID> T getObject(int system, int group, ObjectTypes type, int id, Class<T> toCast)
    {
        if(!checkID(system, group, type, id))
            return null;
        return (T) systems.get(system).getLeft().get(group).get(type).get(id);
    }

    private <T extends SerializableID> T getSysObject(int system, SysObjectTypes type, int id, Class<T> toCast)
    {
        if(!checkSysID(system, type, id))
            return null;
        return (T) systems.get(system).getRight().get(type).get(id);
    }

    public SmokeDetector getSmokeDetector(int system, int group, int id)
    {
        return getObject(system, group, ObjectTypes.smokeDetector, id, SmokeDetector.class);
    }

    public StickyPiston getStickyPiston(int system, int group, int id)
    {
        return getObject(system, group, ObjectTypes.stickyPiston, id, StickyPiston.class);
    }

    public Alarm getAlarm(int system, int id)
    {
        return getSysObject(system, SysObjectTypes.alarm, id, Alarm.class);
    }
}
