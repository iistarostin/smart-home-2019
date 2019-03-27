package ru.sbt.mipt.oop;

public class Alarm extends SmartHomeSimple {

    AlarmBase alarmState;

    public Alarm(String id) {
        super(id);
        alarmState = new AlarmBase();
    }

    public void activate(String code) {
        alarmState = alarmState.activate(code);
    }

    public void deactivate(String code) throws InvalidCodeException{
        alarmState = alarmState.deactivate(code);
    }

    public void raise() {
        alarmState = alarmState.raise();
    }

    public boolean isActive() {
        return alarmState.isActive();
    }
    public boolean isInactive() {
        return alarmState.isInactive();
    }
    public boolean isRaised() {
        return alarmState.isRaised();
    }
}
