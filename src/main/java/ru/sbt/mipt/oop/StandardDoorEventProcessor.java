package ru.sbt.mipt.oop;

import java.util.List;
import java.util.function.BiConsumer;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class StandardDoorEventProcessor extends SmartHomeEventProcessor {

    public StandardDoorEventProcessor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    public void process(String elementID, SensorEventType eventType)
    {
        if (eventType == DOOR_OPEN) {
            // событие от двери
            openDoor(elementID);
        }
        if (eventType == DOOR_CLOSED) {
            // событие от двери
            closeDoor(elementID);
        }
    }
    protected void openDoor(String elementID) {
        smartHome.applyComposite(elementID, new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement current, List<SmartHomeElement> parents) {
                if (current instanceof Door) {
                    Door door = (Door) current;
                    Room room = (Room) parents.get(parents.size() - 1);
                    door.setOpen(true);
                    System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
                }
            }
        });
    }
    protected void closeDoor(String elementID) {
        smartHome.applyComposite(elementID, new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement current, List<SmartHomeElement> parents) {
                if (current instanceof Door) {
                    Door door = (Door) current;
                    Room room = (Room) parents.get(parents.size() - 1);
                    door.setOpen(false);
                    System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
                }
            }
        });
    }
}
