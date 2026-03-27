package green1;

public class CameraEvent {
    private final String cameraId;
    private final String location;
    private final String Offence;
    private final EnvironmentType environmentType;
    public CameraEvent(String cameraId,
                            String location,
                            String Offence,
                            EnvironmentType environmentType) {
        this.cameraId = cameraId;
        this.location = location;
        this.Offence = Offence;
        this.environmentType = environmentType;

    }

    public String getSensorId() { return cameraId; }
    public String getLocation() { return location; }

    public EnvironmentType getEnvironmentType() { return environmentType; }
    public String getOffence() { return Offence; }
}