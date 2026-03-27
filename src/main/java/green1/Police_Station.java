package green1;
import org.greenrobot.eventbus.Subscribe;
import java.util.Set;
import java.util.Random;
public class Police_Station{

    private final String displayName;
    private final Set<EnvironmentType> followedTypes;
    private final int fine;

    public Police_Station(String displayName,
                          Set<EnvironmentType> followedTypes, int fine) {
        this.displayName = displayName;
        this.followedTypes = followedTypes;
        this.fine=fine;

    }

    @Subscribe
    public void onCameraEvent(CameraEvent event) {
        if (!followedTypes.contains(event.getEnvironmentType())) {
            return; // ignore this event
        }
    Random rand= new Random();
    if(event.getEnvironmentType()==EnvironmentType.SURVEILLANCE)
        {
            System.out.println("Sectia de politie locala  "+displayName+ "a emis o amenda in valoare de "+fine+" in locatia "+event.getLocation());
        }
    if(event.getEnvironmentType()==EnvironmentType.SPEEDING)
    {
        System.out.println("Sectai de politie rutiera---"+displayName+ " a emis o amenda pentru contraventia "+event.getOffence()+" in valoare de "+(fine+rand.nextInt(1,300)*2)+"in locatia "+event.getLocation());
    }
    }
}

