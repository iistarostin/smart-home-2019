package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ControlCommandAllLightsOnTest {

    @Test
    void execute() {
        SmartHome smartHome = HomeBuilder.generateSmartHome();
        boolean commandSent[] = new boolean[1];
        commandSent[0] = true;
        SensorCommandSender sensorCommandSender = new SensorCommandSender() {
            @Override
            public void sendCommand(SensorCommand command) {
                commandSent[0] = commandSent[0] && (command.getType() == SensorCommandType.LIGHT_ON);
            }
        };
        ControlCommand command = new ControlCommandAllLightsOff(smartHome, sensorCommandSender);
        command.execute();
        smartHome.apply(new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Light) {
                    assert ((Light) smartHomeElement).isOn();
                }
            }
        });
        assert commandSent[0];
    }
}