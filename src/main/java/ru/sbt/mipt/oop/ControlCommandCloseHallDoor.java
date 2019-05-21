package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandCloseHallDoor implements ControlCommand {
    SmartHome smartHome;
    SensorCommandSender sensorCommandSender;

    public ControlCommandCloseHallDoor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    @Override
    public void execute() {
        smartHome.applyComposite("hall", new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Door) {
                    ((Door) smartHomeElement).setOpen(false);
                    SensorCommand command = new SensorCommand(SensorCommandType.DOOR_CLOSE, smartHomeElement.getId());
                    sensorCommandSender.sendCommand(command);
                }
            }
        });
    }
}
