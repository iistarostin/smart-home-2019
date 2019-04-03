package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

class ControlCommandAlarmActivateTest {

    @Test
    void execute() {
        SmartHome smartHome = HomeBuilder.generateSmartHome();
        boolean commandSent[] = new boolean[1];
        SensorCommandSender sensorCommandSender = new SensorCommandSender() {
            @Override
            public void sendCommand(SensorCommand command) {
                commandSent[0] = (command.getType() == SensorCommandType.ALARM_ACTIVATE);
            }
        };
        ControlCommand command = new ControlCommandAlarmActivate(smartHome, sensorCommandSender, "1");
        command.execute();
        assert smartHome.getAlarm().getState().equals(AlarmState.ACTIVE);
        smartHome.getAlarm().deactivate("1"); //assert code
        assert smartHome.getAlarm().getState().equals(AlarmState.INACTIVE);
        assert  commandSent[0];
    }
}