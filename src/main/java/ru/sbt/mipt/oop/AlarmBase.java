package ru.sbt.mipt.oop;

public class AlarmBase {

    private String code;

    public AlarmBase() {
        this.code = null;
    }

    public AlarmBase(String code) {
        this.code = code;
    }

    private boolean codeOK(String code) {
        return code.equals(this.code);
    }

    public AlarmBase activate(String code) {
        return new AlarmBase(code) {
            @Override
            public boolean isActive() {
                return true;
            }
            @Override
            public boolean isInactive() {
                return false;
            }
            @Override
            public boolean isRaised() {
                return false;
            }
        };

    }
    public AlarmBase deactivate(String code) throws InvalidCodeException{
        if (codeOK(code)) {
            return new AlarmBase(null) {
                @Override
                public boolean isActive() {
                    return false;
                }
                @Override
                public boolean isInactive() {
                    return true;
                }
                @Override
                public boolean isRaised() {
                    return false;
                }
            };
        }
        else {
            throw new InvalidCodeException();
        }
    }
    public AlarmBase raise() {
        return new AlarmBase(this.code) {
            @Override
            public boolean isActive() {
                return false;
            }
            @Override
            public boolean isInactive() {
                return false;
            }
            @Override
            public boolean isRaised() {
                return true;
            }
        };
    }
    public boolean isActive() {
        return false;
    }
    public boolean isInactive() {
        return true;
    }
    public boolean isRaised() {
        return false;
    }
}
