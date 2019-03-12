package ru.sbt.mipt.oop;

public class SmartHomeSearch<T extends  SmartHomeElement> {
    public final Room room;
    public final T element;

    public SmartHomeSearch(Room room, T element) {
        this.room = room;
        this.element = element;
    }

    public static SmartHomeSearch<Light> searchLightByID(SmartHome smartHome, String id) {
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                if (light.getId().equals(id)) {
                    return new SmartHomeSearch<Light>(room, light);
                }
            }
        }
        return null;
    }
    public static SmartHomeSearch<Door> searchDoorByID(SmartHome smartHome, String id) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id)) {
                    return new SmartHomeSearch<Door>(room, door);
                }
            }
        }
        return null;
    }
}
