package ru.sbt.mipt.oop;

import java.util.List;
import java.util.function.BiConsumer;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class LightEventProcessor extends SmartHomeEventProcessor {

    public LightEventProcessor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    public void process(String elementID, SensorEventType eventType)
    {
        if (eventType == LIGHT_ON) {
            // событие от двери
            turnLightOn(elementID);
        }
        if (eventType == LIGHT_OFF) {
            // событие от двери
            turnLightOff(elementID);
        }
    }
    protected void turnLightOn(String elementID) {
        smartHome.applyComposite(elementID, new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement current, List<SmartHomeElement> parents) {
                if (current instanceof Light) {
                    Light light = (Light) current;
                    Room room = (Room) parents.get(parents.size() - 1);
                    light.setOn(true);
                    System.out.println("Light " + light.getId() + " in room " + room.getId() + " was turned on.");
                }
            }
        });
    }
    protected void turnLightOff(String elementID) {
        smartHome.applyComposite(elementID, new BiConsumer<SmartHomeElement, List<SmartHomeElement>>() {
            @Override
            public void accept(SmartHomeElement current, List<SmartHomeElement> parents) {
                if (current instanceof Light) {
                    Light light = (Light) current;
                    Room room = (Room) parents.get(parents.size() - 1);
                    light.setOn(false);
                    System.out.println("Light " + light.getId() + " in room " + room.getId() + " was turned off.");
                }
            }
        });
    }
}
