package green1;

import java.util.Set;
import org.greenrobot.eventbus.EventBus;
import java.util.Random;
import java.lang.Runnable;

public class MainSensors {

    public static void main(String[] args) {

        EventBus eventBus = EventBus.getDefault();

        // Displays
Random rand=new Random();
int fine= rand.nextInt(1,100);
Police_Station ps1 =
                new Police_Station("Sectia1",
                        Set.of(EnvironmentType.SURVEILLANCE, EnvironmentType.SPEEDING),fine);
Police_Station ps2 =
                new Police_Station("Sectia3",
                        Set.of(EnvironmentType.SPEEDING),fine);
Police_Station ps4 =
                new Police_Station("Sectia4",
                        Set.of(EnvironmentType.SURVEILLANCE), fine);
Police_Station ps3 = new Police_Station("Centrul de Urgenta 112",
                Set.of(EnvironmentType.TRAFFIC), 0);
DashBoard db=new DashBoard("InfoTimisoara");
Maps maps1=new Maps("Moovit", EnvironmentType.TRAFFIC);
Maps maps2=new Maps("Waze", EnvironmentType.TRAFFIC);
Maps monitorizareDrone = new Maps("Sistem Monitorizare Drone", EnvironmentType.TRAFFIC);

 eventBus.register(ps1);
 eventBus.register(ps2);
 eventBus.register(ps4);
 eventBus.register(db);
 eventBus.register(ps3);
 eventBus.register(monitorizareDrone);
 eventBus.register(maps1);
 eventBus.register(maps2);

        // Sensors
        CameraSensor cs1 =
                new CameraSensor(eventBus, "B-SB",
                        "Timisoara, Bulevardul Simion Barnutiu","Ambuteliaj provocat de un stalp picat", EnvironmentType.TRAFFIC);

        CameraSensor cs2 =
                new CameraSensor(eventBus, "O-20",
                        "Timisoara, Strada Ofcea, nr 20","TM70OOP, viteza in jur de 100km/h" ,EnvironmentType.SPEEDING);
        CameraSensor cs5 =
                new CameraSensor(eventBus, "C-12",
                        "Timisoara, Circumvalatiunii, nr 12","TM28TIA, viteza in jur de 70km/h" ,EnvironmentType.SPEEDING);
	  CameraSensor cs3 =
                new CameraSensor(eventBus, "B-LV",
                        "Timisoara, Bulevardul Liviu Rebreanu.","Accident dintre doua masini, un sofer a intrat pe contrasens.", EnvironmentType.TRAFFIC);

        CameraSurveillance cs4 =
                new CameraSurveillance(eventBus, "PC0",
                        "Timisoara, Parcul rozelor", "Gunoi aruncat pe jos.");
        CameraSurveillance cs6 =
                new CameraSurveillance(eventBus, "B-MV",
                        "Timisoara, Bulevardul Mihai Viteazu ", "Gunoi aruncat pe jos.");
        CameraSensor cs7 = new CameraSensor(eventBus, "VAM-01",
                "Vama Cenad", "Trafic intens la intrarea in tara", EnvironmentType.TRAFFIC);

        CameraSensor cs8 = new CameraSensor(eventBus, "A1-KM500",
                "Autostrada A1, km 500", "Viteza detectata: 180 km/h", EnvironmentType.SPEEDING);


        CameraSurveillance cs9 = new CameraSurveillance(eventBus, "MUZ-SEC",
                "Muzeul de Arta", "Tentativa de patrundere prin efractie");


        CameraSensor cs10 = new CameraSensor(eventBus, "POD-01",
                "Podul Andrei Saguna", "Lucrari la carosabil - drum ingustat", EnvironmentType.TRAFFIC);

        // Simulate
        cs1.setCameraRecord();
        cs2.setCameraRecord();
        cs3.setCameraRecord();
        cs5.setCameraRecord();
        cs7.setCameraRecord();
        cs8.setCameraRecord();
        cs10.setCameraRecord();

        Thread t1=new Thread(cs4);
        t1.start();

        Thread t2=new Thread(cs6);
        t2.start();

        Thread t3=new Thread(cs9);
        t3.start();
        try{
            Thread.sleep(4000);
            cs4.running=false;
            cs6.running=false;
            cs9.running=false;
            t1.join();
            t2.join();
            t3.join();
        }catch( InterruptedException e)
        {

            throw new RuntimeException(e);
        }

    }
}