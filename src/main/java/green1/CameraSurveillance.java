package green1;

import org.greenrobot.eventbus.EventBus;
import java.lang.Runnable;
public class CameraSurveillance implements Runnable{
    private final EventBus eventBus;
    private final String Cameraid;
    private final String location;
    private final String Offence;

    public CameraSurveillance(EventBus eventBus,
                              String Cameraid,
                              String location,
                              String Offence
    ) {
        this.eventBus = eventBus;
        this.Cameraid = Cameraid;
        this.location = location;
        this.Offence=Offence;
    }
    public volatile boolean running=true;

    public void run()
    {
        while(running)
        {
            try {
                CameraEvent event = new CameraEvent(Cameraid, location, Offence, EnvironmentType.SURVEILLANCE);
                eventBus.post(event);
                Thread.sleep(2000);
            }catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                running=false;
            }
        }
    }
}
