package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandAllLightsOff implements ControlCommand {
    SmartHome smartHome;
    SensorCommandSender sensorCommandSender;

    public ControlCommandAllLightsOff(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    @Override
    public void execute() {
        smartHome.applyComposite(smartHome.getId(), new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Light) {
                    ((Light) smartHomeElement).setOn(false);
                    SensorCommand command = new SensorCommand(SensorCommandType.LIGHT_OFF, smartHomeElement.getId());
                    sensorCommandSender.sendCommand(command);
                }
            }
        });
    }
}
