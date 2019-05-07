package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;
import rc.RemoteControl;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ProgrammableRemoteControlTest {

    @Test
    void onButtonPressed() {
        RemoteControl testRC = new ProgrammableRemoteControl();
        int[] state = new int[1];
        ((ProgrammableRemoteControl) testRC).bind("inc", new ControlCommand(null, null) {
            @Override
            void execute() {
                state[0] += 1;
            }
        });
        ((ProgrammableRemoteControl) testRC).bind("dec", new ControlCommand(null, null) {
            @Override
            void execute() {
                state[0] -= 1;
            }
        });
        ((ProgrammableRemoteControl) testRC).bind("add5", new ControlCommand(null, null) {
            @Override
            void execute() {
                state[0] += 5;
            }
        });
        testRC.onButtonPressed("dec", "");
        testRC.onButtonPressed("add5", "");
        testRC.onButtonPressed("inc", "");
        assert (state[0] == 5);

        //rebind
        ((ProgrammableRemoteControl) testRC).bind("inc", new ControlCommand(null, null) {
            @Override
            void execute() {
                state[0] += 2;
            }
        });
        testRC.onButtonPressed("inc", "");
        assert (state[0] == 7);
    }
}