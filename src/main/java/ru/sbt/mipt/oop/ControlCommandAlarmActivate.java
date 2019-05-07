package ru.sbt.mipt.oop;

public class ControlCommandAlarmActivate implements ControlCommand {
    Alarm alarm;
    SensorCommandSender sensorCommandSender;

    String code;
    public ControlCommandAlarmActivate(SmartHome smartHome, SensorCommandSender sensorCommandSender, String code) {
        this.alarm = smartHome.getAlarm();
        this.sensorCommandSender = sensorCommandSender;
        this.code = code; //Without this parameter it is unclear with which code should the alarm be activated
    }
    public ControlCommandAlarmActivate(Alarm alarm, SensorCommandSender sensorCommandSender, String code) {
        this.alarm = alarm;
        this.sensorCommandSender = sensorCommandSender;
        this.code = code; //Without this parameter it is unclear with which code should the alarm be activated
    }

    @Override
    public void execute() {
        alarm.activate(code);
        SensorCommand command = new SensorCommand(SensorCommandType.ALARM_ACTIVATE, alarm.getId());
        sensorCommandSender.sendCommand(command);
    }
}
