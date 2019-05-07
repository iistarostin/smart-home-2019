package ru.sbt.mipt.oop;

public class ControlCommandAlarmActivate extends ControlCommand {

    String code;
    public ControlCommandAlarmActivate(SmartHome smartHome, SensorCommandSender sensorCommandSender, String code) {
        super(smartHome, sensorCommandSender);
        this.code = code; //Without this parameter it is unclear with which code should the alarm be activated
    }

    @Override
    void execute() {
        smartHome.getAlarm().activate(code);
        SensorCommand command = new SensorCommand(SensorCommandType.ALARM_ACTIVATE, smartHome.getAlarm().getId());
        sensorCommandSender.sendCommand(command);
    }
}
