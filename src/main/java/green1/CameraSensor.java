package green1;

import org.greenrobot.eventbus.EventBus;
import java.lang.Runnable;


public class CameraSensor {
    private final EventBus eventBus;
    private final String Cameraid;
    private final String location;
    private final String Offence;
    private final EnvironmentType environmentType;


    public CameraSensor(EventBus eventBus,
                             String Cameraid,
                             String location,
                             String Offence,
                             EnvironmentType environmentType
                             ) {
        this.eventBus = eventBus;
        this.Cameraid = Cameraid;
        this.location = location;
        this.Offence=Offence;
        this.environmentType = environmentType;
    }

    public void setCameraRecord() {

            CameraEvent event =
                    new CameraEvent(Cameraid, location,Offence,  environmentType);
            eventBus.post(event);
    }
}

