package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Room extends SmartHomeComposite {
    private Collection<Light> lights;
    private Collection<Door> doors;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        super(name);
        this.lights = lights;
        this.doors = doors;
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public String getName() {
        return getId();
    }

    @Override
    public Iterator<SmartHomeElement> getIterator() {
        ArrayList<SmartHomeElement> children = new ArrayList<>();
        children.addAll(lights);
        children.addAll(doors);
        return children.iterator();
    }
}
