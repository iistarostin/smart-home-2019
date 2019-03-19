package ru.sbt.mipt.oop;

public class GuardedSmartHomeEventHandler extends SmartHomeEventHandlerDecorator {
    SmartHome smartHome;
    public GuardedSmartHomeEventHandler(SmartHome smartHome, SensorCommandSender sensorCommandSender) {
        super(smartHome, sensorCommandSender);
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (smartHome.getAlarm().isRaised()) {
            System.out.println("Sending sms");
        }
        else if (smartHome.getAlarm().isActive()) {
            if (event.getType() != SensorEventType.ALARM_DEACTIVATE) {
                new AlarmEventProcessor(smartHome).raiseAlarm(smartHome.getAlarm().getId());
                System.out.println("Sending sms");
            }
        } else {
            super.handleEvent(event);
        }
    }
}
