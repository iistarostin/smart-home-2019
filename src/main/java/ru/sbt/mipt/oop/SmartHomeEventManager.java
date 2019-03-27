package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHomeEventManager {
    private SensorEventReceiver sensorEventReceiver;
    private Collection<SmartHomeEventHandler> handlersPool;

    public SmartHomeEventManager(SensorEventReceiver sensorEventReceiver) {
        this.sensorEventReceiver = new SensorEventGenerator();
        handlersPool = new ArrayList<>();
    }

    public void addEventHandler(SmartHomeEventHandler handler) {
        this.handlersPool.add(handler);
    }

    public void start() {
        SensorEvent event;
        while ((event = sensorEventReceiver.getNextSensorEvent()) != null) {
            System.out.println("Got event {type: " + event.getType().toString() + "; id: " + event.getObjectId() + "}");
            for (SmartHomeEventHandler handler : handlersPool) {
                handler.handleEvent(event);
            }
        }
    }
}
