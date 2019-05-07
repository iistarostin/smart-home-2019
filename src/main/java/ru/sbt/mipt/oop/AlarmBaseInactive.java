package ru.sbt.mipt.oop;

public class AlarmBaseInactive extends AlarmBase {

    public AlarmBaseInactive() { super(null); }

    @Override
    public AlarmState getState() {
        return AlarmState.INACTIVE;
    }

    @Override
    public AlarmBase activate(String code) {
        return new AlarmBaseActive(code);
    }
}
