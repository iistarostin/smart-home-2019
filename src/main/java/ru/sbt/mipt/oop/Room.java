package ru.sbt.mipt.oop;

import java.util.*;

public class Room extends SmartHomeComposite {
    private Collection<Light> lights;
    private Collection<Door> doors;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        super(name);
        this.lights = lights;
        for (Light light : this.lights) {
            light.setParent(this);
        }
        this.doors = doors;
        for (Door door : this.doors) {
            door.setParent(this);
        }
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
        List<SmartHomeElement> children = new LinkedList<>();
        children.addAll(lights);
        children.addAll(doors);
        return children.iterator();
    }
}
