package ru.sbt.mipt.oop;

public class SmartHomeEventHandlerDecorator implements SmartHomeEventHandler {
    SmartHomeEventHandler smartHomeEventHandler;
    public SmartHomeEventHandlerDecorator(SmartHomeEventHandler base) {
        smartHomeEventHandler = base;
    }
    public void handleEvent(SensorEvent event) {
        smartHomeEventHandler.handleEvent(event);
    }
}
