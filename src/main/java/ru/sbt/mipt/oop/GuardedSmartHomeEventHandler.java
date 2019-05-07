package ru.sbt.mipt.oop;

public class GuardedSmartHomeEventHandler extends SmartHomeEventHandlerDecorator {
    SmartHome smartHome;
    public GuardedSmartHomeEventHandler(SmartHomeEventHandler base, SmartHome smartHome) {
        super(base);
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (smartHome.getAlarm().getState() == AlarmState.RAISED) {
            System.out.println("Sending sms");
        }
        else if (smartHome.getAlarm().getState() == AlarmState.ACTIVE) {
            if (event.getType() != SensorEventType.ALARM_DEACTIVATE) {
                smartHome.getAlarm().raise();
                System.out.println("Sending sms");
            }
        } else {
            super.handleEvent(event);
        }
    }
}
