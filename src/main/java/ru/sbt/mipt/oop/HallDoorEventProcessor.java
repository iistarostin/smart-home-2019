package ru.sbt.mipt.oop;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class HallDoorEventProcessor extends StandardDoorEventProcessor{

    private SensorCommandSender sensorCommandSender;
    public HallDoorEventProcessor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome);
        this.sensorCommandSender = sensorCommandSender;
    }

    @Override
    public void closeDoor(String elementID) {
        super.closeDoor(elementID);
        turnAllLightsOff(smartHome, sensorCommandSender);
    }

    private void turnAllLightsOff(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        smartHome.applyComposite(smartHome.getId(), new Consumer<SmartHomeElement>() {
            @Override
            public void accept(SmartHomeElement smartHomeElement) {
                if (smartHomeElement instanceof Light) {
                    ((Light) smartHomeElement).setOn(false);
                    SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, smartHomeElement.getId());
                    sensorCommandSender.sendCommand(command);
                }
            }
        });
    }
}
