package ru.sbt.mipt.oop;

public abstract class ControlCommand {
    SmartHome smartHome;
    SensorCommandSender sensorCommandSender;

    public ControlCommand(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    abstract void execute();
}
