package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandCorridorLightOn extends ControlCommand {

    public ControlCommandCorridorLightOn(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    @Override
    void execute() {
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
