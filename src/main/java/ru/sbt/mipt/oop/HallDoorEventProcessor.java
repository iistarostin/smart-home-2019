package ru.sbt.mipt.oop;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;

public class HallDoorEventProcessor implements SmartHomeEventHandler{

    private SmartHome smartHome;
    private SensorCommandSender sensorCommandSender;
    public HallDoorEventProcessor(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        this.smartHome = smartHome;
        this.sensorCommandSender = sensorCommandSender;
    }

    private void turnAllLightsOff() {
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

    @Override
    public void handleEvent(SensorEvent event) {
        SensorEventType eventType = event.getType();
        String elementID = event.getObjectId();
        if (eventType == DOOR_CLOSED && elementID == "hall") {
            turnAllLightsOff();
        }
    }
}
