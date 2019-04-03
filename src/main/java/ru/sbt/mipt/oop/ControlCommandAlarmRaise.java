package ru.sbt.mipt.oop;

import java.util.function.Consumer;

public class ControlCommandAlarmRaise extends ControlCommand {

    public ControlCommandAlarmRaise(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    @Override
    void execute() {
        smartHome.getAlarm().raise();
        SensorCommand command = new SensorCommand(CommandType.ALARM_RAISE, smartHome.getAlarm().getId());
    }
}
