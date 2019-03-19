package ru.sbt.mipt.oop;

import java.util.List;
import java.util.function.Consumer;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class StandardDoorEventProcessor {

    SmartHome smartHome;
    public StandardDoorEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    public void openDoor(String elementID) {
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Door) {
                    Door door = (Door) current;
                    Room room = (Room) current.getParent();
                    door.setOpen(true);
                    System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
                }
            }
        });
    }

    public void closeDoor(String elementID) {
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Door) {
                    Door door = (Door) current;
                    Room room = (Room) current.getParent();
                    door.setOpen(false);
                    System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
                }
            }
        });
    }
}
