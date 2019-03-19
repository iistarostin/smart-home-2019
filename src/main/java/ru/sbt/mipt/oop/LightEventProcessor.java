package ru.sbt.mipt.oop;

import java.util.List;
import java.util.function.Consumer;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class LightEventProcessor {

    SmartHome smartHome;
    public LightEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    public void turnLightOn(String elementID) {
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Light) {
                    Light light = (Light) current;
                    Room room = (Room) current.getParent();
                    light.setOn(true);
                    System.out.println("Light " + light.getId() + " in room " + room.getId() + " was turned on.");
                }
            }
        });
    }
    public void turnLightOff(String elementID) {
        smartHome.applyComposite(elementID, new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement current) {
                if (current instanceof Light) {
                    Light light = (Light) current;
                    Room room = (Room) current.getParent();
                    light.setOn(false);
                    System.out.println("Light " + light.getId() + " in room " + room.getId() + " was turned off.");
                }
            }
        });
    }
}
