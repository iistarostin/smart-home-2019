package ru.sbt.mipt.oop;

public abstract class AlarmBase {

    protected String code;

    public AlarmBase(String code) {
        this.code = code;
    }

    protected boolean codeOK(String code) {
        if (code == null) {
            return true;
        }
        return code.equals(this.code);
    }

    public AlarmBase activate(String code) {
        return this;
    }
    public AlarmBase deactivate(String code) {
        return this;
    }
    public AlarmBase raise() {
        return new AlarmBaseRaised(this.code);
    }

    public abstract AlarmState getState();
}
