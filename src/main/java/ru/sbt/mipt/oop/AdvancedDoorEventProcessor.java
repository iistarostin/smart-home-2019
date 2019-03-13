package ru.sbt.mipt.oop;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class AdvancedDoorEventProcessor extends StandardDoorEventProcessor{

    public AdvancedDoorEventProcessor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    @Override
    protected void closeDoor(String elementID) {
        AtomicBoolean isHall = new AtomicBoolean(false); //create wrapper to change value within anonymous class
        smartHome.applyComposite(elementID, new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement current, List<SmartHomeElement> parents) {
                if (current instanceof Door) {
                    Door door = (Door) current;
                    Room room = (Room) parents.get(parents.size() - 1);
                    door.setOpen(false);
                    System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
                    if (room.getName() == "hall") {
                        isHall.set(true);
                    }
                }
            }
        });

        if (isHall.get()) {
            // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
            // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
            turnAllLightsOff(smartHome, sensorCommandSender);
        }
    }

    private void turnAllLightsOff(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        smartHome.applyComposite(smartHome.getId(), new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement, List<SmartHomeElement> smartHomeElements) {
                if (smartHomeElement instanceof Light) {
                    ((Light) smartHomeElement).setOn(false);
                    SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, smartHomeElement.getId());
                    sensorCommandSender.sendCommand(command);
                }
            }
        });
    }
}
