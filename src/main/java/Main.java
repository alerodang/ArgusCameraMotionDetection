import nu.pattern.OpenCV;

public class Main {

    public static void main(String[] args) throws Exception {
        String capturesFolder = System.getenv("CAPTURES_FOLDER");
        String mqHost = System.getenv("MQ_HOST");
        System.out.println(mqHost);
        OpenCV.loadShared();
        Recorder recorder = new Recorder(capturesFolder, mqHost);
        recorder.start();
    }
}
