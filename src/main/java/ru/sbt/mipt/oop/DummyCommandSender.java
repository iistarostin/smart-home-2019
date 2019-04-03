package ru.sbt.mipt.oop;

public class DummyCommandSender implements SensorCommandSender {
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretend we're sending command " + command);
    }
}
