package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class StandardSmartHomeEventHandler implements SmartHomeEventHandler {
    private SmartHome smartHome;
    private SensorCommandSender sensorCommandSender;

    public StandardSmartHomeEventHandler(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    public void handleEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        if (event.getType() == LIGHT_ON) {
            // событие от источника света
            turnLight(smartHome, event.getObjectId(), true);
        }
        if (event.getType() == LIGHT_OFF) {
            // событие от источника света
            turnLight(smartHome, event.getObjectId(), false);
        }
        if (event.getType() == DOOR_OPEN) {
            // событие от двери
            moveDoor(smartHome, event.getObjectId(), true);
        }
        if (event.getType() == DOOR_CLOSED) {
            // событие от двери
            moveDoor(smartHome, event.getObjectId(), false);
        }
    }

    private void moveDoor(SmartHome smartHome, String doorId, boolean open) {
        SmartHomeSearch<Door> door = SmartHomeSearch.searchDoorByID(smartHome, doorId);
        if (open) {
            door.element.setOpen(true);
            System.out.println("Door " + door.element.getId() + " in room " + door.room.getName() + " was opened.");
        } else {
            door.element.setOpen(false);
            System.out.println("Door " + door.element.getId() + " in room " + door.room.getName() + " was closed.");
            // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
            // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
            if (door.room.getName().equals("hall")) {
              turnAllLightsOff(smartHome);
            }
        }
    }

    private void turnAllLightsOff(SmartHome smartHome) {
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                sensorCommandSender.sendCommand(command);
            }
        }
    }

    private void turnLight(SmartHome smartHome, String lightId, boolean turnOn) {
        SmartHomeSearch<Light> light = SmartHomeSearch.searchLightByID(smartHome, lightId);
        if (turnOn) {
            light.element.setOn(true);
            System.out.println("Light " + light.element.getId() + " in room " + light.room.getName() + " was turned on.");
        } else {
            light.element.setOn(false);
            System.out.println("Light " + light.element.getId() + " in room " + light.room.getName() + " was turned off.");
        }
    }
}
