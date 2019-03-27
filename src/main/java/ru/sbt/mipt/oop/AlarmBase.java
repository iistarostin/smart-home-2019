package ru.sbt.mipt.oop;

public abstract class AlarmBase {

    private String code;

    public AlarmBase(String code) {
        this.code = code;
    }

    protected boolean codeOK(String code) {
        return code.equals(this.code);
    }

    public AlarmBase activate(String code) {
        return this;
    }
    public AlarmBase deactivate(String code) {
        return this;
    }
    public AlarmBase raise() {
        return this;
    }
    public abstract AlarmState getState();
}
