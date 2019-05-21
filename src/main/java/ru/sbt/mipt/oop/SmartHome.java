package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SmartHome extends SmartHomeComposite {
    Alarm alarm;
    Collection<Room> rooms;

    public SmartHome(String id) {
        super(id);
        alarm = null;
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        super("root");
        alarm = null;
        this.rooms = rooms;
        for (Room room : this.rooms) {
            room.setParent(this);
        }
    }

    public SmartHome(Collection<Room> rooms, Alarm alarm) {
        this(rooms);
        this.alarm = alarm;
    }

    public void addRoom(Room room) {
        room.setParent(this);
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
    }
    public Alarm getAlarm() { return this.alarm; }

    @Override
    public Iterator<? extends SmartHomeElement> getIterator() {
        List<SmartHomeElement> children = new LinkedList<>();
        if (alarm != null) {
            children.addAll(rooms);
        }
        return children.iterator();
    }
}
