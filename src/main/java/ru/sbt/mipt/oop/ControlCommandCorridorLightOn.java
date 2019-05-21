package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandCorridorLightOn implements ControlCommand {
    SmartHome smartHome;
    SensorCommandSender sensorCommandSender;

    public ControlCommandCorridorLightOn(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    @Override
    public void execute() {
        smartHome.applyComposite("hall", new Consumer<SmartHomeElement>() { //assuming hall and corridor are the same room
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Light) {
                    ((Light) smartHomeElement).setOn(true);
                    SensorCommand command = new SensorCommand(SensorCommandType.LIGHT_ON, smartHomeElement.getId());
                    sensorCommandSender.sendCommand(command);
                }
            }
        });
    }
}
