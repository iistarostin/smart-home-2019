package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandCloseHallDoor extends ControlCommand {

    public ControlCommandCloseHallDoor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    @Override
    void execute() {
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
