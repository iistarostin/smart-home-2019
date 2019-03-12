package ru.sbt.mipt.oop;

import java.io.IOException;

public interface SmartHomeReaderWriter {
    public SmartHome readSmartHome() throws IOException;
    public void writeSmartHome(SmartHome smartHome) throws IOException;
}

