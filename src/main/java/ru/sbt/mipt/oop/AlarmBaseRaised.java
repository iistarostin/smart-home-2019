package ru.sbt.mipt.oop;

public class AlarmBaseRaised extends AlarmBase {
    public AlarmBaseRaised(String code) {
        super(code);
    }

    @Override
    public AlarmBase deactivate(String code) {
        if (codeOK(code)) {
            return new AlarmBaseInactive();
        }
        return this;
    }
    @Override
    public AlarmState getState() {
        return AlarmState.RAISED;
    }
}
