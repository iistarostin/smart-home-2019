package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlCommandAlarmRaiseTest {

    @Test
    void execute() {
        SmartHome smartHome = HomeBuilder.generateSmartHome();
        boolean commandSent[] = new boolean[1];
        SensorCommandSender sensorCommandSender = new SensorCommandSender() {
            @Override
            public void sendCommand(SensorCommand command) {
                commandSent[0] = (command.getType() == SensorCommandType.ALARM_RAISE);
            }
        };
        smartHome.getAlarm().activate("1");
        ControlCommand command = new ControlCommandAlarmRaise(smartHome, sensorCommandSender);
        command.execute();
        assert smartHome.getAlarm().getState().equals(AlarmState.RAISED);
        smartHome.getAlarm().deactivate("1"); //assert code
        assert smartHome.getAlarm().getState().equals(AlarmState.INACTIVE);
        assert  commandSent[0];
    }
}