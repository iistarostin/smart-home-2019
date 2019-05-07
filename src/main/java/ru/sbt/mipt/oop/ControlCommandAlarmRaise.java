package ru.sbt.mipt.oop;

public class ControlCommandAlarmRaise extends ControlCommand {

    public ControlCommandAlarmRaise(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
    }

    @Override
    void execute() {
        smartHome.getAlarm().raise();
        SensorCommand command = new SensorCommand(SensorCommandType.ALARM_RAISE, smartHome.getAlarm().getId());
        sensorCommandSender.sendCommand(command);
    }
}
