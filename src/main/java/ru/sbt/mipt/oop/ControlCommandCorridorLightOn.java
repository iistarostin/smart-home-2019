package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandCorridorLightOn extends ControlCommand {

    public ControlCommandCorridorLightOn(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    @Override
    void execute() {
        smartHome.applyComposite("corridor", new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Light) {
                    ((Light) smartHomeElement).setOn(true);
                    SensorCommand command = new SensorCommand(CommandType.LIGHT_ON, smartHomeElement.getId());
                    sensorCommandSender.sendCommand(command);
                }
            }
        });
    }
}
