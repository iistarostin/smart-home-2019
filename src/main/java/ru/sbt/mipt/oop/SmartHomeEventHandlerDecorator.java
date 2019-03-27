package ru.sbt.mipt.oop;

public class SmartHomeEventHandlerDecorator implements SmartHomeEventHandler {
    SmartHomeEventHandler smartHomeEventHandler;
    public SmartHomeEventHandlerDecorator(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        smartHomeEventHandler = new StandardSmartHomeEventHandler(smartHome, sensorCommandSender);
    }
    public void handleEvent(SensorEvent event) {
        smartHomeEventHandler.handleEvent(event);
    }
}
