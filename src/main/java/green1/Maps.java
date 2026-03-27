package green1;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;
public class Maps {
    private final String appsName;
    private Set<String> locations=new HashSet<String>();
    private final EnvironmentType typeEnv;

    public Maps(String appsName,EnvironmentType typeEnv)
    {
        this.appsName=appsName;
        this.typeEnv=typeEnv;
    }
    @Subscribe
    public void setMaps(CameraEvent event)
    {
        if (typeEnv!=event.getEnvironmentType()) {
            return; // ignore this event
        }
        locations.add(event.getLocation());
        System.out.println(" Aplicatia : "+appsName+"\t-locatiile sunt "+locations);
    }
}
