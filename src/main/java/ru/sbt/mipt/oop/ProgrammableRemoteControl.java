package ru.sbt.mipt.oop;
import rc.RemoteControl;
import java.util.HashMap;

public class ProgrammableRemoteControl implements RemoteControl {

    String id;
    HashMap<String, ControlCommand> binds;

    public ProgrammableRemoteControl(String id) {
        this.id = id;
        binds = new HashMap<>();
    }

    public ProgrammableRemoteControl() {
        this.binds = new HashMap<>();
        this.id = ""; //default id
    }

    public void bind(String buttonCode, ControlCommand command) {
        binds.put(buttonCode, command);
    }

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        //rcId - unused parameter
        if (binds.containsKey(buttonCode)) {
            binds.get(buttonCode).execute();
        }
    }
}
