package ru.sbt.mipt.oop;

public class Alarm extends SmartHomeSimple {

    AlarmBase alarmState;

    public Alarm(String id) {
        super(id);
        alarmState = new AlarmBaseInactive();
    }

    public void activate(String code) {
        alarmState = alarmState.activate(code);
    }
    public void deactivate(String code) { alarmState = alarmState.deactivate(code); }
    public void raise() {
        alarmState = alarmState.raise();
    }

    public AlarmState getState() { return alarmState.getState(); }
}
