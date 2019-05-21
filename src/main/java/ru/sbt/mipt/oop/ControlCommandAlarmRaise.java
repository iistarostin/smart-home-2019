package ru.sbt.mipt.oop;

public class ControlCommandAlarmRaise implements ControlCommand {
    Alarm alarm;
    SensorCommandSender sensorCommandSender;

    public ControlCommandAlarmRaise(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.alarm = smartHome.getAlarm();
        this.sensorCommandSender = sensorCommandSender;
    }

    public ControlCommandAlarmRaise(Alarm alarm, SensorCommandSender sensorCommandSender) {
        this.alarm = alarm;
        this.sensorCommandSender = sensorCommandSender;
    }

    @Override
    public void execute() {
        alarm.raise();
        SensorCommand command = new SensorCommand(SensorCommandType.ALARM_RAISE, alarm.getId());
        sensorCommandSender.sendCommand(command);
    }
}
