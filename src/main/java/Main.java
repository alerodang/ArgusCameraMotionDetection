import nu.pattern.OpenCV;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String capturesFolder = args[0];
        OpenCV.loadShared();
        Recorder recorder = new Recorder(capturesFolder);
        recorder.start();
    }
}
