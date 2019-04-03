package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandAllLightsOn extends ControlCommand {

    public ControlCommandAllLightsOn(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    @Override
    void execute() {
        smartHome.applyComposite(smartHome.getId(), new Consumer<SmartHomeElement>() {
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
