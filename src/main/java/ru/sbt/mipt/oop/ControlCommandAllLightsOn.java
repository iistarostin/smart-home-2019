package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandAllLightsOn implements ControlCommand {
    SmartHome smartHome;
    SensorCommandSender sensorCommandSender;

    public ControlCommandAllLightsOn(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    @Override
    public void execute() {
        smartHome.applyComposite(smartHome.getId(), new Consumer<SmartHomeElement>() {
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
