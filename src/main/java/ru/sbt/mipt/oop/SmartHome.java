package ru.sbt.mipt.oop;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SmartHome extends SmartHomeComposite {
    Collection<Room> rooms;

    public SmartHome(String id) {
        super(id);
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        super("root");
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    @Override
    public Iterator<? extends SmartHomeElement> getIterator() {
        return rooms.iterator();
    }

    public void applyComposite(String id, BiConsumer<SmartHomeElement, List<SmartHomeElement>> action) {
        List<SmartHomeElement> parents = new LinkedList<>();
        parents.add(this);
        applyComposite(id, action, parents);
    }
}
