package green1;
import java.util.HashSet;
import java.util.Set;

import org.greenrobot.eventbus.Subscribe;

public class DashBoard
{
    private final String nameofTheDashBoard;
    private static int numberFinesSpeeding=0;
    private static int numberFineSurveillance=0;
    private final Set<String> locations=new HashSet<String>();

    public DashBoard(String nameofTheDashBoard)
    {
        this.nameofTheDashBoard=nameofTheDashBoard;
    }

    @Subscribe
    public void setDashBoard(CameraEvent event)
    {
        if(event.getEnvironmentType()==EnvironmentType.SURVEILLANCE)
            synchronized (DashBoard.class) {
                numberFineSurveillance++;
            }
        if(event.getEnvironmentType()==EnvironmentType.SPEEDING)
            numberFinesSpeeding++;
        Afisari(locations.add(event.getLocation()));
    }
    public void Afisari(boolean b) {
        if (b == true && (numberFineSurveillance != 0 || numberFinesSpeeding != 0)) {
            System.out.println(" Dashboardul orasului " + nameofTheDashBoard + " contine:\n- un numar de " + numberFinesSpeeding + " amenzi pentru viteza\n" + "- un numar de " + numberFineSurveillance + " amenzi pentru surveillance\n");
        }
        if (b == true)
            System.out.println(" Locatiile cele mai periculoase \t-" + locations);
        System.out.println("----------------------------------");
    }

}
