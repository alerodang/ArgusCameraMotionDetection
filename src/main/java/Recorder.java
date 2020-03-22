import org.opencv.core.Mat;

import java.util.concurrent.TimeUnit;

class Recorder {

    private CameraService cameraService;
    private ImageService imageService;

    Recorder(String directory) {
        this.cameraService = new CameraService();
        this.imageService = new ImageService(directory);
    }

    void start() throws InterruptedException {
        cameraService.openCamera(1920, 1080);
        this.imageService.setFolder();
        Mat previousGrayFrame = cameraService.getGrayScaleFrame(cameraService.getFrame());
        Mat newFrame;
        Mat newFrameGrayScale;

        while (true) {
            newFrame = cameraService.getFrame();
            newFrameGrayScale = cameraService.getGrayScaleFrame(newFrame);

            if (cameraService.detectMovement(previousGrayFrame, newFrameGrayScale, 5000)) {
                System.out.println("Motion detected!!!");
                this.imageService.saveImage(newFrame);
            }

            previousGrayFrame = newFrameGrayScale;


            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
