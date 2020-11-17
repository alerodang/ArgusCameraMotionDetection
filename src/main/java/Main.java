import nu.pattern.OpenCV;

public class Main {

    public static void main(String[] args) throws Exception {
        String capturesFolder = System.getenv("CAPTURES_FOLDER");
        String mqHost = System.getenv("MQ_HOST");
        String movementSensibility = System.getenv("MOVEMENT_SENSIBILITY");
        String millisecondsBetweenCaptures = System.getenv("MILLISECONDS_BETWEEN_CAPTURES");
        System.out.println(mqHost);
        OpenCV.loadLocally();
        Recorder recorder = new Recorder(capturesFolder, mqHost, Integer.parseInt(movementSensibility), Integer.parseInt(millisecondsBetweenCaptures));
        recorder.start();
    }
}
