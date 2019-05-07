package ru.sbt.mipt.oop;

public class AlarmBaseActive extends AlarmBase {

    public AlarmBaseActive(String code) {
        super(code);
    }

    @Override
    public AlarmState getState() {
        return AlarmState.ACTIVE;
    }

    @Override
    public AlarmBase deactivate(String code) {
        if (codeOK(code)) {
            return new AlarmBaseInactive();
        }
        else {
            return new AlarmBaseRaised(this.code);
        }
    }
}
