package ru.sbt.mipt.oop;

public abstract class SmartHomeEventProcessor {
    protected SmartHome smartHome;
    protected SensorCommandSender sensorCommandSender;

    public SmartHomeEventProcessor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    public abstract void process(String elementID, SensorEventType eventType);
}
