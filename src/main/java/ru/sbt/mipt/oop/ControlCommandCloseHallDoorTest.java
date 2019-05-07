package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ControlCommandCloseHallDoorTest {

    @Test
    void execute() {
        SmartHome smartHome = HomeBuilder.generateSmartHome();
        boolean commandSent[] = new boolean[1];
        commandSent[0] = true;
        SensorCommandSender sensorCommandSender = new SensorCommandSender() {
            @Override
            public void sendCommand(SensorCommand command) {
                commandSent[0] = commandSent[0] && (command.getType() == SensorCommandType.DOOR_CLOSE);
            }
        };
        smartHome.applyComposite("hall", new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Door) {
                    ((Door) smartHomeElement).setOpen(true);
                }
            }
        });
        ControlCommand command = new ControlCommandCloseHallDoor(smartHome, sensorCommandSender);
        command.execute();
        smartHome.applyComposite("hall", new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Door) {
                    assert !((Door) smartHomeElement).isOpen();
                }
            }
        });
        assert commandSent[0];
    }
}